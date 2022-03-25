package hu.bertokattila.pt.user.controllers;

import hu.bertokattila.pt.user.LoginRequestDTO;
import hu.bertokattila.pt.user.LoginResponseDTO;
import hu.bertokattila.pt.auth.AuthUser;
import hu.bertokattila.pt.auth.util.JwtUtil;
import hu.bertokattila.pt.user.service.UserDetailsService;
import hu.bertokattila.pt.user.service.UserService;
import hu.bertokattila.pt.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping()
public class UserController {
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private final JwtUtil jwtUtil;
  @Autowired
  public UserController(UserService userService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil){
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/register")
  public void addSession(@Valid @RequestBody UserDTO user){
    userService.saveUser(user);
  }

  @GetMapping("/hello")
  public int hello(){
    return ((AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    //return "Hello";
  }

  @PostMapping("/login")
  public ResponseEntity<?> createToken(@Valid @RequestBody LoginRequestDTO loginDTO) throws Exception {
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
      );
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect email or password", e);
    }
    UserDetails userDetails = userDetailsService
            .loadUserByUsername(loginDTO.getEmail());
    String jwt = jwtUtil.generateToken((AuthUser) userDetails);
    return ResponseEntity.ok(new LoginResponseDTO(jwt));
  }

}
