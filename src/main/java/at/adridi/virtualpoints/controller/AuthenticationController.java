package at.adridi.virtualpoints.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.adridi.virtualpoints.dto.RegistrationRequest;
import at.adridi.virtualpoints.services.AuthenticationService;
import lombok.AllArgsConstructor;

/**
 * 
 * Do registration of users (Owners)
 * 
 * @author A.Dridi
 *
 */
@RestController
@RequestMapping("/api/authentication")
@AllArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/registration")
	public ResponseEntity<String> registration(@RequestBody RegistrationRequest registrationRequest) {
		switch (authenticationService.registration(registrationRequest)) {
		case 0:
			return new ResponseEntity<>("OK. Registration of user " + registrationRequest.getEmail() + " was sucessfull!",
					HttpStatus.OK);
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
}
