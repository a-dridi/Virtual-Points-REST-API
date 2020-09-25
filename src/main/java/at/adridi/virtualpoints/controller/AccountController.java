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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.adridi.virtualpoints.DataValueNotFoundException;
import at.adridi.virtualpoints.models.Account;
import at.adridi.virtualpoints.services.AccountService;
import lombok.NoArgsConstructor;

/**
 * Manage and save virtual points accounts
 * 
 * @author A.Dridi
 *
 */
@RestController
@RequestMapping("/api/account")
@NoArgsConstructor
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/all")
	public ResponseEntity<List<Account>> getAllAccount() {
		List<Account> accounts = accountService.getAllAccount();
		if (!CollectionUtils.isEmpty(accounts)) {
			return status(HttpStatus.OK).body(accounts);
		} else {
			return status(HttpStatus.BAD_REQUEST).body(new ArrayList<Account>());
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
		try {
			return status(HttpStatus.OK).body(accountService.getAccountById(id));
		} catch (DataValueNotFoundException e) {
			return status(HttpStatus.BAD_REQUEST).body(new Account());
		}
	}

	@PostMapping("/open")
	public ResponseEntity<String> openAccount(@RequestBody Account account) {
		if (this.accountService.save(account)) {
			return ResponseEntity.ok("A new account was opened successfully. ");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Account could not be opened!");
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
		if (this.accountService.update(account)) {
			return ResponseEntity.ok(account);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(account);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
		if (this.accountService.deleteById(id)) {
			return ResponseEntity.ok("Your account was deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Account Id does not exists!");
		}
	}

	@GetMapping("/widthdraw/{id}/{amount}")
	public ResponseEntity<String> widthdrawFromAccount(@PathVariable Long id, @PathVariable Integer amount) {
		try {
			Integer newBalance = this.accountService.widthdraw(id, amount);
			return ResponseEntity.ok(amount + " were widthdrawn. You have now a balance of " + newBalance + ".");
		} catch (DataValueNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/deposit/{id}/{amount}")
	public ResponseEntity<String> depositOnAccount(@PathVariable Long id, @PathVariable Integer amount) {
		try {
			Integer newBalance = this.accountService.deposit(id, amount);
			return ResponseEntity.ok(amount + " were deposit. You have now a balance of " + newBalance + ".");
		} catch (DataValueNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/deposit/{senderAccountId}/{recipientAccountId}/{amount}")
	public ResponseEntity<String> depositOnAccount(@PathVariable Long senderAccountId, @PathVariable Long recipientAccountId, @PathVariable Integer amount) {
		try {
			Integer newBalance = this.accountService.transfer(senderAccountId, recipientAccountId, amount);
			return ResponseEntity.ok(amount + " were transfered to the account number" + recipientAccountId + ". You have now a balance of " + newBalance + ".");
		} catch (DataValueNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
