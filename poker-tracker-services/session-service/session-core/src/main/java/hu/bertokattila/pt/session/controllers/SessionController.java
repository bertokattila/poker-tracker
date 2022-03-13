package hu.bertokattila.pt.session.controllers;

import hu.bertokattila.pt.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

  @Autowired
  private SessionService sessionService;

  @PostMapping
  public void addSession(){
    sessionService.hello();
  }
}