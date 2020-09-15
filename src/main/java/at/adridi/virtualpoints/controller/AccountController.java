package at.adridi.virtualpoints.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.adridi.virtualpoints.models.Account;
import at.adridi.virtualpoints.services.AccountService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

	private final AccountService accountService;
	
	@PostMapping
	public ResponseEntity<Void> openAccount(@RequestBody Account account) {
		this.accountService.save(account);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Account>> getAllAccount(){
		return status(HttpStatus.OK).body(accountService.getAllAccount());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable Long id){
		return status(HttpStatus.OK).body(accountService.getAccountById(id));
	}
}
