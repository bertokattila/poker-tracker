package hu.bertokattila.pt.session.controllers;

import hu.bertokattila.pt.session.SessionDTO;
import hu.bertokattila.pt.session.model.Session;
import hu.bertokattila.pt.session.service.SessionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/session")
public class SessionController {
  private final SessionService sessionService;
  @Autowired
  public SessionController(SessionService sessionService){
    this.sessionService = sessionService;
  }

  @PostMapping
  @Valid
  public ResponseEntity<?> addSession(@Valid @RequestBody SessionDTO session) {
    try {
      sessionService.saveSession(session);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getSession(@PathVariable int id) {
    Session res;
    try {
      res = sessionService.getSession(id).orElse(null);
      if(res == null) throw new NotFoundException("Session does not exists");
    }
    catch (NotFoundException e){
      return new ResponseEntity<>("Session with id " + id + " does not exists", HttpStatus.NOT_FOUND);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteSession(@PathVariable int id){
    try {
      sessionService.deleteSession(id);
    }
    catch (EmptyResultDataAccessException e){
      return new ResponseEntity<>("Session with id " + id + " does not exists", HttpStatus.NOT_FOUND);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>("Session deleted with id: " + id, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  @Valid
  public ResponseEntity<?> updateSession(@Valid @RequestBody SessionDTO session, @PathVariable int id) {
    try {
      Session sessionToModify = sessionService.getSession(id).orElse(null);
      if(sessionToModify == null) throw new NotFoundException("Session does not exists");

      sessionService.updateSession(sessionToModify, session);
    }
    catch (NotFoundException e){
      return new ResponseEntity<>("Session with id " + id + " does not exists", HttpStatus.NOT_FOUND);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
