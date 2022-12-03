package hu.bertokattila.pt.session.controllers;

import hu.bertokattila.pt.session.ExtendedSessionDTO;
import hu.bertokattila.pt.session.GetSessionsDTO;
import hu.bertokattila.pt.session.PublicSessionsDTO;
import hu.bertokattila.pt.session.SessionDTO;
import hu.bertokattila.pt.session.model.Session;
import hu.bertokattila.pt.session.service.SessionService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class SessionController {
  private final SessionService sessionService;

  @Autowired
  public SessionController(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @PostMapping("/session")
  @Valid
  public ResponseEntity<?> addSession(@Valid @RequestBody SessionDTO session) {
    Session savedSess = sessionService.saveSession(session);
    sessionService.refreshStatistics(savedSess);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/session/{id}")
  public ResponseEntity<?> getSession(@PathVariable int id) {
    Session res;
    try {
      res = sessionService.getSession(id).orElse(null);
      if (res == null) throw new Exception("Session does not exists");
    } catch (Exception e) {
      return new ResponseEntity<>("Session with id " + id + " does not exists", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @DeleteMapping("/session/{id}")
  public ResponseEntity<String> deleteSession(@PathVariable int id) {
    try {
      if (!sessionService.deleteSession(id)) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
    } catch (EmptyResultDataAccessException e) {
      return new ResponseEntity<>("Session with id " + id + " does not exists", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/session/{id}")
  @Valid
  public ResponseEntity<?> updateSession(@Valid @RequestBody SessionDTO session, @PathVariable int id) {
    try {
      Session sessionToModify = sessionService.getSession(id).orElse(null);
      if (sessionToModify == null) throw new Exception("Session does not exists");

      sessionService.updateSession(sessionToModify, session);
    } catch (Exception e) {
      return new ResponseEntity<>("Session with id " + id + " does not exists", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/sessions")
  public ResponseEntity<?> getSessions(@Valid @Positive @RequestParam int limit, @Valid @PositiveOrZero @RequestParam int offset) {
    return new ResponseEntity<>(sessionService.getSessionsForLoggedInUser(new GetSessionsDTO(limit, offset)), HttpStatus.OK);
  }

  @GetMapping("/internal/publicsessions")
  public ResponseEntity<?> getSessions(@RequestParam List<Integer> userIds, @Valid @Positive @RequestParam int limit, @Valid @PositiveOrZero @RequestParam int offset) {
    return new ResponseEntity<>(sessionService.getPublicSessionsOfUsers(new PublicSessionsDTO(userIds, offset, limit)), HttpStatus.OK);
  }

  @GetMapping("/internal/sessions/{userId}")
  public ResponseEntity<ExtendedSessionDTO[]> getSessionsForUSer(@Valid @PositiveOrZero @PathVariable int userId) {
    return new ResponseEntity<>(sessionService.getSessionsForUser(userId), HttpStatus.OK);
  }
}
