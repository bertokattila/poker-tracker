package hu.bertokattila.pt.user.controllers;

import hu.bertokattila.pt.auth.AuthUser;
import hu.bertokattila.pt.auth.util.JwtUtil;
import hu.bertokattila.pt.user.LoginRequestDTO;
import hu.bertokattila.pt.user.LoginResponseDTO;
import hu.bertokattila.pt.user.UserDTO;
import hu.bertokattila.pt.user.UserIdDTO;
import hu.bertokattila.pt.user.UserPublicDataDTO;
import hu.bertokattila.pt.user.model.User;
import hu.bertokattila.pt.user.service.UserDetailsService;
import hu.bertokattila.pt.user.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@CrossOrigin(origins = "*")
public class UserController {
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private final JwtUtil jwtUtil;

  @Autowired
  public UserController(UserService userService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/register")
  public void addSession(@Valid @RequestBody UserDTO user) {
    userService.saveUser(user);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> createToken(@Valid @RequestBody LoginRequestDTO loginDTO) throws Exception {
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
    return ResponseEntity.ok(new LoginResponseDTO(jwt, ((AuthUser) userDetails).getName(), ((AuthUser) userDetails).getDefaultCurrency()));
  }

  @GetMapping("/id")
  public ResponseEntity<UserIdDTO> getIdForEmail(@RequestParam String email) throws Exception {
    UserIdDTO userIdDTO = new UserIdDTO();
    User user = userService.getUserByEmail(email);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    userIdDTO.setId(user.getId());
    return ResponseEntity.ok(userIdDTO);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<UserPublicDataDTO> getIdForEmail(@PathVariable int id) {
    UserPublicDataDTO userDTO = new UserPublicDataDTO();
    User user = userService.getUserById(id);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    userDTO.setId(user.getId());
    userDTO.setName(user.getName());
    userDTO.setEmail(user.getEmail());
    userDTO.setDefaultCurrency(user.getDefaultCurrency());
    return ResponseEntity.ok(userDTO);
  }
}

