package at.adridi.virtualpoints.services;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import at.adridi.virtualpoints.dto.RegistrationRequest;
import at.adridi.virtualpoints.models.Owner;
import at.adridi.virtualpoints.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

	private final PasswordEncoder passwordEncoder;
	private final OwnerRepository ownerRepository;

	/**
	 * Save user as "Owner" object in the database.
	 * 
	 * @param registrationRequest
	 * @return 0: OK, 1: ConstraintVilationExcpetion - Email already exists, 2:
	 *         Empty form field value(s), 3: Other exception
	 */
	@Transactional
	public Integer registration(RegistrationRequest registrationRequest) {
		Owner newOwner = new Owner();
		if (registrationRequest.getForename().trim().equals("") 
				|| registrationRequest.getSurname().trim().equals("")
				|| registrationRequest.getEmail().trim().equals("")
				|| registrationRequest.getPassword().trim().equals("")) {
			return 2;
		}
		newOwner.setForename(registrationRequest.getForename());
		newOwner.setSurname(registrationRequest.getSurname());
		newOwner.setEmail(registrationRequest.getEmail());
		newOwner.setPassword(this.passwordEncoder.encode(registrationRequest.getPassword()));
		try {
			ownerRepository.save(newOwner);
			log.info("New user (" + newOwner.getEmail() + ") registered. ");	
			return 0;
		} catch (ConstraintViolationException e) {
			log.error(e.getMessage());
			return 1;
		} catch (Exception e) {
			log.error(e.getMessage());
			return 3;
		}
	}
}
