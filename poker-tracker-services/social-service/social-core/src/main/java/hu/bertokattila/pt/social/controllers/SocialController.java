package hu.bertokattila.pt.social.controllers;

import hu.bertokattila.pt.social.AddFriendDTO;
import hu.bertokattila.pt.social.FriendDTO;
import hu.bertokattila.pt.social.service.SocialService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/social")
public class SocialController {
  private final SocialService socialService;

  @Autowired
  public SocialController(SocialService socialService) {
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
    if (friends == null || friends.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(friends, HttpStatus.OK);
  }

  /**
   * Friends you added, not yet accepted
   */
  @GetMapping("/addedfriends")
  public ResponseEntity<List<FriendDTO>> addedFriends() {
    List<FriendDTO> friends = socialService.getAddedFriendsForLoggedInUser();
    if (friends == null || friends.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(friends, HttpStatus.OK);
  }

  /**
   * Users who requested to be your friend, not yet accepted
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
    if (socialService.acceptFriendRequest(socialConnectionId)) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping("/notifications")
  public ResponseEntity<?> getSessions(@Valid @Positive @RequestParam int limit, @Valid @PositiveOrZero @RequestParam int offset) {
    return new ResponseEntity<>(socialService.getNotifications(limit, offset), HttpStatus.OK);
  }
}
