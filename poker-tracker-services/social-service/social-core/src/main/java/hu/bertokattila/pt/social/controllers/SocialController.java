package hu.bertokattila.pt.social.controllers;

import hu.bertokattila.pt.session.PublicSessionsDTO;
import hu.bertokattila.pt.social.AddFriendDTO;
import hu.bertokattila.pt.social.FriendDTO;
import hu.bertokattila.pt.social.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/social")
public class SocialController {
  private final SocialService socialService;
  @Autowired
  public SocialController(SocialService socialService){
    this.socialService = socialService;
  }

  @PostMapping("/addfriend")
  @Valid
  public ResponseEntity<?> addSession(@Valid @RequestBody AddFriendDTO addFriendDTO) {
    return socialService.addFriend(addFriendDTO.getEmail());
  }

  @GetMapping("/friends")
  public ResponseEntity<List<FriendDTO>> friends() {
    List<FriendDTO> friends = socialService.getFriendsForLoggedInUser();
    if(friends == null || friends.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(friends, HttpStatus.OK);
  }

  /**
   * Friends you added, not yet accepted
   * @return
   */
  @GetMapping("/addedfriends")
  public ResponseEntity<List<FriendDTO>> addedFriends() {
    List<FriendDTO> friends = socialService.getAddedFriendsForLoggedInUser();
    if(friends == null || friends.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(friends, HttpStatus.OK);
  }

  /**
   * Users who requested to be your friend, not yet accepted
   * @return
   */
  @GetMapping("/friendrequests")
  public ResponseEntity<List<FriendDTO>> friendRequests() {
    List<FriendDTO> friends = socialService.getFriendRequestsForLoggedInUser();
    if (friends == null || friends.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(friends, HttpStatus.OK);
  }


  @PostMapping("/acceptfriend/{socialConnectionId}")
  @Valid
  public ResponseEntity<?> acceptFriend(@Valid @PathVariable int socialConnectionId) {
    if(socialService.acceptFriendRequest(socialConnectionId)){
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping("/notifications")
  public ResponseEntity<?> getSessions(@Valid @Positive @RequestParam int limit, @Valid @PositiveOrZero @RequestParam int offset) {
    return new ResponseEntity<>(socialService.getNotifications(limit, offset), HttpStatus.OK);
  }
}
