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
import at.adridi.virtualpoints.models.AccountType;
import at.adridi.virtualpoints.services.AccountTypeService;
import lombok.NoArgsConstructor;

/**
 * Type of virtual points account. 
 * @author A.Dridi
 *
 */
@RestController
@RequestMapping("/api/accounttype")
@NoArgsConstructor
public class AccountTypeController {

	@Autowired
	private AccountTypeService accountTypeService;
	
	@GetMapping("/all")
	public ResponseEntity<List<AccountType>> getAllAccountType() {
		List<AccountType> accountTypes = this.accountTypeService.getAllAccountType();
		if(!CollectionUtils.isEmpty(accountTypes)) {
			return status(HttpStatus.OK).body(accountTypes);
		} else {
			return status(HttpStatus.BAD_REQUEST).body(new ArrayList<AccountType>());
		}
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<AccountType> getAccountTypeById(@PathVariable @Valid Long id){
		try {
			return status(HttpStatus.OK).body(accountTypeService.getAccountTypeById(id));
		} catch (DataValueNotFoundException e) {
			return status(HttpStatus.BAD_REQUEST).body(new AccountType());
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<AccountType> updateAccountType(@PathVariable @Valid Long id, @Valid @RequestBody AccountType accountType) {
		if (this.accountTypeService.update(accountType)) {
			return ResponseEntity.ok(accountType);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(accountType);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAccountType(@PathVariable @Valid Long accountTypeId) {
		if (this.accountTypeService.deleteById(accountTypeId)) {
			return ResponseEntity.ok("Your account type was deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Account type Id does not exists!");
		}
	}
	
}
