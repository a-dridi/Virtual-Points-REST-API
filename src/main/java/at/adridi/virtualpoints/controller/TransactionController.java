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
import at.adridi.virtualpoints.models.Transaction;
import at.adridi.virtualpoints.services.TransactionService;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/transaction")
@NoArgsConstructor

/**
 * Manage and load transactions of accounts. 
 * @author A.Dridi
 *
 */
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Transaction>> getAllTransaction() {
		List<Transaction> transactionList = this.transactionService.getAllTransaction();
		if(!CollectionUtils.isEmpty(transactionList)) {
			return status(HttpStatus.OK).body(transactionList);
		} else {
			return status(HttpStatus.BAD_REQUEST).body(new ArrayList<Transaction>());
		}
	}
	
	@GetMapping("/get/byId/{id}")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id){
		try {
			return status(HttpStatus.OK).body(this.transactionService.getTransactionById(id));
		} catch (DataValueNotFoundException e) {
			return status(HttpStatus.BAD_REQUEST).body(new Transaction());
		}
	}


	@GetMapping("/get/byAccountId/{accountId}")
	public ResponseEntity<List<Transaction>> getAllTransactionByAccountId(@PathVariable Long accountId) {
		List<Transaction> transactionOfAccountIdList = this.transactionService.getAllTransactionsByAccountId(accountId);
		if(!CollectionUtils.isEmpty(transactionOfAccountIdList)) {
			return status(HttpStatus.OK).body(transactionOfAccountIdList);
		} else {
			return status(HttpStatus.BAD_REQUEST).body(new ArrayList<Transaction>());
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @Valid @RequestBody Transaction transaction) {
		if (this.transactionService.update(transaction)) {
			return ResponseEntity.ok(transaction);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Transaction());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteTransaction(@PathVariable Long transactionId) {
		if (this.transactionService.deleteById(transactionId)) {
			return ResponseEntity.ok("Your transaction was deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Transaction Id does not exists!");
		}
	}
}
