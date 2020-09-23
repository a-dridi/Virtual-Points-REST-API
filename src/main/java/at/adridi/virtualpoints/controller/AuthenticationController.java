package at.adridi.virtualpoints.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.adridi.virtualpoints.models.LoginRequest;
import at.adridi.virtualpoints.models.LoginResponse;
import at.adridi.virtualpoints.models.RegistrationRequest;
import at.adridi.virtualpoints.services.AuthenticationService;
import at.adridi.virtualpoints.services.OwnerUserDetailsService;
import at.adridi.virtualpoints.util.JwtUtil;
import lombok.AllArgsConstructor;

/**
 * 
 * Do login or registration of users (Owner).
 * 
 * @author A.Dridi
 *
 */
@RestController
@RequestMapping("/api/authentication")
@AllArgsConstructor
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private final AuthenticationService authenticationService;
	private OwnerUserDetailsService ownerUserDetailsService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/registration")
	public ResponseEntity<String> registration(@RequestBody RegistrationRequest registrationRequest) {
		switch (authenticationService.registration(registrationRequest)) {
		case 0:
			return new ResponseEntity<>(
					"OK. Registration of user " + registrationRequest.getEmail() + " was sucessfull!", HttpStatus.OK);
		case 1:
			return new ResponseEntity<>("Email address already exists!", HttpStatus.BAD_REQUEST);
		case 2:
			return new ResponseEntity<>("Please fill in all fields!", HttpStatus.BAD_REQUEST);
		case 3:
			return new ResponseEntity<>(
					"User could not be registered! Please check if you did enter all fields correctly. ",
					HttpStatus.BAD_REQUEST);
		default:
			return new ResponseEntity<>(
					"User could not be registered! Please check if you did enter all fields correctly. ",
					HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Does create a JWT authentication token, if the user was successfully logged
	 * in and authenticated.
	 * 
	 * @param loginRequest
	 * @return
	 * @throws Exception If login failed, then this exception is thrown.
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
		try {
			 this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Login failed. Please enter correct username and password.");
		}
		
		final UserDetails authenticatedUserDetails = ownerUserDetailsService.loadUserByUsername(loginRequest.getEmail());
		final String jwtToken = jwtUtil.generateToken(authenticatedUserDetails);
		return ResponseEntity.ok(new LoginResponse(jwtToken));
	}
}
