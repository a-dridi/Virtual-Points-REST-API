package at.adridi.virtualpoints.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.adridi.virtualpoints.DataValueNotFoundException;
import at.adridi.virtualpoints.models.Owner;
import at.adridi.virtualpoints.services.OwnerService;
import lombok.NoArgsConstructor;

/**
 * Saves Owner of accounts. It is also a user account.
 * 
 * @author A.Dridi
 *
 */

@RestController
@RequestMapping("/api/owner")
@NoArgsConstructor
public class OwnerController {

	@Autowired
	private OwnerService ownerService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Owner>> getAllOwner() {
		List<Owner> ownerList = this.ownerService.getAllOwner();
		if(!CollectionUtils.isEmpty(ownerList)) {
			return status(HttpStatus.OK).body(ownerList);
		} else {
			return status(HttpStatus.BAD_REQUEST).body(new ArrayList<Owner>());
		}
	}
	
	@GetMapping("/get/byId/{id}")
	public ResponseEntity<Owner> getOwnerById(@PathVariable Long id){
		try {
			return status(HttpStatus.OK).body(this.ownerService.getOwnerById(id));
		} catch (DataValueNotFoundException e) {
			return status(HttpStatus.BAD_REQUEST).body(new Owner());
		}
	}

	@GetMapping("/get/byEmail/{email}")
	public ResponseEntity<Owner> getOwnerByEmail(@PathVariable String email){
		try {
			return status(HttpStatus.OK).body(this.ownerService.getOwnerByEmail(email));
		} catch (DataValueNotFoundException e) {
			return status(HttpStatus.BAD_REQUEST).body(new Owner());
		}
	}	
	
	@GetMapping("/get/byForename/{forename}")
	public ResponseEntity<List<Owner>> getAllOwnerByForename(@PathVariable String forename) {
		List<Owner> ownerList = this.ownerService.getOwnerByForename(forename);
		if(!CollectionUtils.isEmpty(ownerList)) {
			return status(HttpStatus.OK).body(ownerList);
		} else {
			return status(HttpStatus.BAD_REQUEST).body(new ArrayList<Owner>());
		}
	}
	
	@GetMapping("/get/bySurname/{surname}")
	public ResponseEntity<List<Owner>> getAllOwnerBySurname(@PathVariable String surname) {
		List<Owner> ownerList = this.ownerService.getOwnerBySurname(surname);
		if(!CollectionUtils.isEmpty(ownerList)) {
			return status(HttpStatus.OK).body(ownerList);
		} else {
			return status(HttpStatus.BAD_REQUEST).body(new ArrayList<Owner>());
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
		if (this.ownerService.update(owner)) {
			return ResponseEntity.ok(owner);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Owner());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAccountType(@PathVariable Long ownerId) {
		if (this.ownerService.deleteById(ownerId)) {
			return ResponseEntity.ok("Your owner was deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Owner Id does not exists!");
		}
	}
	
}
