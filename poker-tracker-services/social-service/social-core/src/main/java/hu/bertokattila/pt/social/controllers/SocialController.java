package hu.bertokattila.pt.social.controllers;

import hu.bertokattila.pt.social.AddFriendDTO;
import hu.bertokattila.pt.social.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

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
    socialService.addFriend(addFriendDTO.getEmail());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
