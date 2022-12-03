package hu.bertokattila.pt.social.service;


import hu.bertokattila.pt.auth.AuthUser;
import hu.bertokattila.pt.session.ExtendedSessionDTO;
import hu.bertokattila.pt.social.FriendDTO;
import hu.bertokattila.pt.social.SessionAndOwnerDTO;
import hu.bertokattila.pt.social.config.ServiceUrlProperties;
import hu.bertokattila.pt.social.data.SocialConnectionRepository;
import hu.bertokattila.pt.social.model.SocialConnectionRec;
import hu.bertokattila.pt.user.UserIdDTO;
import hu.bertokattila.pt.user.UserPublicDataDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service

public class SocialService {
  private final SocialConnectionRepository repository;
  private final ServiceUrlProperties serviceUrlProperties;

  @Autowired
  public SocialService(SocialConnectionRepository repository, ServiceUrlProperties serviceUrlProperties) {
    this.repository = repository;
    this.serviceUrlProperties = serviceUrlProperties;
  }

  public List<SessionAndOwnerDTO> getNotifications(int limit, int offset) {
    int currentId = getCurrentId();
    List<Integer> friends = new ArrayList<Integer>();
    List<SocialConnectionRec> connections = repository.findActiveSocialConnections(currentId).stream().toList();
    for (SocialConnectionRec rec : connections) {
      if (rec.getMasterUserId() == currentId) {
        friends.add(rec.getSlaveUserId());
      } else {
        friends.add(rec.getMasterUserId());
      }
    }

    RestTemplate restTemplate = new RestTemplate();
    String url = serviceUrlProperties.getSessionServiceUrl();
    // rosszul konvertalna magatol url formatumba
    String userIdsUrl = "userIds=";
    for (Integer id : friends) {
      userIdsUrl += id.toString();
      userIdsUrl += ",";
    }
    if (userIdsUrl.endsWith(",")) {
      userIdsUrl = userIdsUrl.substring(0, userIdsUrl.length() - 1);
    }
    url += "/internal/publicsessions?" + userIdsUrl;
    ResponseEntity<ExtendedSessionDTO[]> response
            = restTemplate.getForEntity(url + "&limit={limit}&offset={offset}", ExtendedSessionDTO[].class, limit, offset);
    ExtendedSessionDTO[] sessions = response.getBody();
    List<SessionAndOwnerDTO> result = new ArrayList<SessionAndOwnerDTO>();
    for (ExtendedSessionDTO session : sessions) {
      SessionAndOwnerDTO sessionDTO = new SessionAndOwnerDTO();
      sessionDTO.setAccess(session.getAccess());
      sessionDTO.setLocation(session.getLocation());
      sessionDTO.setComment(session.getComment());
      sessionDTO.setEndDate(session.getEndDate());
      sessionDTO.setStartDate(session.getStartDate());
      sessionDTO.setCashOut(session.getCashOut());
      sessionDTO.setBuyIn(session.getBuyIn());
      sessionDTO.setCurrency(session.getCurrency());
      sessionDTO.setType(session.getType());
      sessionDTO.setOwnerEmail(getUserForId(session.getUserId()).getEmail());
      sessionDTO.setAnte(session.getAnte());
      sessionDTO.setBlinds(session.getBlinds());
      sessionDTO.setTableSize(session.getTableSize());
      sessionDTO.setSpecificGameType(session.getSpecificGameType());
      result.add(sessionDTO);
    }
    return result;
  }

  public ResponseEntity<?> addFriend(String email) {
    int currentUserId = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    Integer id = getIdForEmailFromUserService(email);
    if (id == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + email + " not found");
    }
    if (id == currentUserId) {
      return ResponseEntity.badRequest().body("You can't add yourself as a friend");
    }
    if (repository.findByMasterUserIdAndSlaveUserId(currentUserId, id).orElse(null) != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User with email " + email + " is already your friend");
    }
    SocialConnectionRec rec = new SocialConnectionRec();
    rec.setMasterUserId(currentUserId);
    rec.setSlaveUserId(id);
    rec.setActive(false);
    return new ResponseEntity<>(repository.save(rec), HttpStatus.OK);
  }

  public List<FriendDTO> getFriendsForLoggedInUser() {
    int currentUserId = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    return repository.findActiveSocialConnections(currentUserId).stream().map(rec -> {
      FriendDTO dto = new FriendDTO();
      UserPublicDataDTO otherUser;
      if (currentUserId == rec.getMasterUserId()) {
        otherUser = getUserForId(rec.getSlaveUserId());
      } else {
        otherUser = getUserForId(rec.getMasterUserId());
      }
      if (otherUser == null) {
        return null;
      }
      dto.setEmail(otherUser.getEmail());
      dto.setName(otherUser.getName());
      dto.setSocialId(rec.getId());
      return dto;
    }).collect(java.util.stream.Collectors.toList());
  }

  public List<FriendDTO> getAddedFriendsForLoggedInUser() {
    int currentUserId = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    return repository.findByMasterUserIdAndActive(currentUserId, false).stream().map(rec -> {
      FriendDTO dto = new FriendDTO();
      UserPublicDataDTO otherUser;
      otherUser = getUserForId(rec.getSlaveUserId());
      if (otherUser == null) {
        return null;
      }
      dto.setEmail(otherUser.getEmail());
      dto.setName(otherUser.getName());
      dto.setSocialId(rec.getId());
      return dto;
    }).collect(java.util.stream.Collectors.toList());
  }

  public List<FriendDTO> getFriendRequestsForLoggedInUser() {
    int currentUserId = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    return repository.findBySlaveUserIdAndActive(currentUserId, false).stream().map(rec -> {
      FriendDTO dto = new FriendDTO();
      UserPublicDataDTO otherUser;
      otherUser = getUserForId(rec.getMasterUserId());
      if (otherUser == null) {
        return null;
      }
      dto.setEmail(otherUser.getEmail());
      dto.setName(otherUser.getName());
      dto.setSocialId(rec.getId());
      return dto;
    }).collect(java.util.stream.Collectors.toList());
  }

  public Integer getIdForEmailFromUserService(String email) {
    RestTemplate restTemplate = new RestTemplate();
    String url = serviceUrlProperties.getUserServiceUrl();
    ResponseEntity<UserIdDTO> response = null;
    System.out.println(url);
    try {
      response = restTemplate.getForEntity(url + "/id?email=" + email, UserIdDTO.class);
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
        return null;
      }
    }
    if (response == null) {
      return null;
    }
    UserIdDTO user = response.getBody();
    return user.getId();
  }

  public UserPublicDataDTO getUserForId(int id) {
    RestTemplate restTemplate = new RestTemplate();
    String url = serviceUrlProperties.getUserServiceUrl();
    ResponseEntity<UserPublicDataDTO> response = null;
    try {
      response = restTemplate.getForEntity(url + "/user/" + id, UserPublicDataDTO.class);
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
        return null;
      }
    }
    if (response == null) {
      return null;
    }
    UserPublicDataDTO user = response.getBody();
    return user;
  }

  public boolean acceptFriendRequest(int id) {
    int currentUserId = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    SocialConnectionRec rec = repository.findById(id);
    if (rec == null) {
      return false;
    }
    if (rec.getSlaveUserId() != currentUserId) {
      return false;
    }
    rec.setActive(true);
    repository.save(rec);
    return true;
  }

  private int getCurrentId() {
    return ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
  }
}
