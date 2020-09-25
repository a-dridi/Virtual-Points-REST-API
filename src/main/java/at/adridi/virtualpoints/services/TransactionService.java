package at.adridi.virtualpoints.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.adridi.virtualpoints.DataValueNotFoundException;
import at.adridi.virtualpoints.models.Account;
import at.adridi.virtualpoints.models.Transaction;
import at.adridi.virtualpoints.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountService accountService;

	/*
	 * Save new Transaction.
	 * 
	 * @param newTransaction
	 * 
	 * @return true if successful
	 */
	public boolean save(Transaction newTransaction) {
		if (newTransaction == null) {
			return false;
		}
		if (this.transactionRepository.save(newTransaction) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get certain Transaction with the passed id. Throws DataValueNotFoundException
	 * if Transaction is not available.
	 * 
	 * @param id
	 * @return
	 */
	public Transaction getTransactionById(long id) {
		return this.transactionRepository.findByTransactionId(id)
				.orElseThrow(() -> new DataValueNotFoundException("Tranasction Does Not Exist"));
	}

	/**
	 * Get a List of all saved Transaction objects
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Transaction> getAllTransaction() {
		return this.transactionRepository.findAll();
	}

	/**
	 * Get a List of transactions of an account id.
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Transaction> getAllTransactionsByAccountId(long accountId) {
		try {
			Account account = this.accountService.getAccountById(accountId);
			return this.transactionRepository.findBySenderAccount(account).orElseThrow(
					() -> new DataValueNotFoundException("Transactions could not be loaded. Account does not exist."));
		} catch (DataValueNotFoundException | NullPointerException e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Update transaction with the values passed in the object updatedTransaction
	 * 
	 * @param updatedTransaction
	 * @return true if successful
	 */
	public boolean update(Transaction updatedTransaction) {
		if (updatedTransaction == null) {
			return false;
		}
		Transaction transaction = this.getTransactionById(updatedTransaction.getTransactionId());
		if (transaction != null) {
			transaction.setSenderAccount(updatedTransaction.getSenderAccount());
			transaction.setAmount(updatedTransaction.getAmount());
			transaction.setTransactionType(updatedTransaction.getTransactionType());
			transaction.setRecipientAccount(updatedTransaction.getRecipientAccount());
			if (this.transactionRepository.save(transaction) != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Delete an existing transaction
	 * 
	 * @param transactionId
	 * @return true if successful
	 */
	public boolean deleteById(Long transactionId) {
		if (transactionId == null || transactionId==0) {
			return false;
		}
		Transaction transaction = this.getTransactionById(transactionId);
		if (transaction != null) {
			this.transactionRepository.delete(transaction);
			if (this.getTransactionById(transaction.getTransactionId()) != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
