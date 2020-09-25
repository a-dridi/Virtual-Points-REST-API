package at.adridi.virtualpoints.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.adridi.virtualpoints.DataValueNotFoundException;
import at.adridi.virtualpoints.models.Account;
import at.adridi.virtualpoints.models.Transaction;
import at.adridi.virtualpoints.repositories.AccountRepository;
import at.adridi.virtualpoints.util.TransactionTypes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountService {

	@Autowired
	private final AccountRepository accountRepository;
	@Autowired
	private final TransactionService transactionService;
	@Autowired
	private final TransactionTypes transactionTypes;

	/**
	 * Save new account.
	 * 
	 * @param newAccount
	 * @return true if successful
	 */
	public boolean save(Account newAccount) {
		if (newAccount == null) {
			return false;
		}
		if (this.accountRepository.save(newAccount) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get certain account with the passed id. Throws DataValueNotFoundException if
	 * account is not available.
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Account getAccountById(long id) {
		return this.accountRepository.findByAccountId(id)
				.orElseThrow(() -> new DataValueNotFoundException("Account Does Not Exist"));
	}

	/**
	 * Get a List of all saved accounts
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Account> getAllAccount() {
		return this.accountRepository.findAll();
	}

	/**
	 * Update account with the values passed in the object updateAccount
	 * 
	 * @param updatedAccount
	 * @return true if successful
	 */
	public boolean update(Account updatedAccount) {
		if (updatedAccount == null) {
			return false;
		}
		Account account = this.getAccountById(updatedAccount.getAccountId());
		if (account != null) {
			account.setAccountType(updatedAccount.getAccountType());
			account.setBalance(updatedAccount.getBalance());
			account.setOwner(updatedAccount.getOwner());
			account.setTransactions(updatedAccount.getTransactions());
			if (this.accountRepository.save(account) != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Delete an existing account
	 * 
	 * @param accountId
	 * @return true if successful
	 */
	public boolean deleteById(Long accountId) {
		if (accountId == null || accountId == 0) {
			return false;
		}
		Account account = this.getAccountById(accountId);
		if (account != null) {
			this.accountRepository.delete(account);
			if (this.getAccountById(account.getAccountId()) != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Withdraws amount from account with the id accountId
	 * 
	 * @param accountId
	 * @param amount
	 * @return the balance after withdraw.
	 */
	public Integer widthdraw(Long accountId, Integer amount) {
		if (accountId == null || accountId == 0 || amount < 0) {
			throw new DataValueNotFoundException("Account Id cannot be null. Amount must be 0 or bigger.");
		}
		Account account = this.getAccountById(accountId);
		if (account != null) {
			account.setBalance(account.getBalance() - amount);
			this.accountRepository.save(account);
			Transaction newTransaction = new Transaction();
			newTransaction.setAmount(amount);
			newTransaction.setSenderAccount(account);
			newTransaction.setTransactionType(transactionTypes.getTransactionTypeNumber("Withdraw"));
			transactionService.save(newTransaction);
			return account.getBalance() - amount;
		} else {
			throw new DataValueNotFoundException("Account with the account id does not exist.");
		}
	}

	/**
	 * Deposits amount from account with the id accountId
	 * 
	 * @param accountId
	 * @param amount
	 * @return the balance after deposit.
	 */
	public Integer deposit(Long accountId, Integer amount) {
		if (accountId == null || accountId == 0 || amount < 0) {
			throw new DataValueNotFoundException("Account Id cannot be null. Amount must be 0 or bigger.");
		}
		Account account = this.getAccountById(accountId);
		if (account != null) {
			account.setBalance(account.getBalance() + amount);
			this.accountRepository.save(account);
			Transaction newTransaction = new Transaction();
			newTransaction.setAmount(amount);
			newTransaction.setSenderAccount(account);
			newTransaction.setTransactionType(transactionTypes.getTransactionTypeNumber("Deposit"));
			this.transactionService.save(newTransaction);
			return account.getBalance() + amount;
		} else {
			throw new DataValueNotFoundException("Account with the account id does not exist.");
		}
	}

	/**
	 * Transfer amount from account with the id accountId.
	 * 
	 * @param senderAccountId
	 * @param recipientAccountId
	 * @param amount
	 * @return the balance of sender Account after transfer.
	 */
	public Integer transfer(Long senderAccountId, Long recipientAccountId, Integer amount) {
		if (senderAccountId == null || senderAccountId == 0) {
			throw new DataValueNotFoundException("Sender AccountId cannot be null.");
		}
		if (recipientAccountId == null || recipientAccountId == 0) {
			throw new DataValueNotFoundException("Sender AccountId cannot be null.");
		}
		if (amount == null || amount > 0) {
			throw new DataValueNotFoundException("Amount must be 0 or bigger.");
		}
		Account senderAccount = this.getAccountById(senderAccountId);
		if (senderAccountId == null || senderAccountId == 0) {
			throw new DataValueNotFoundException("Sender AccountId does not exist.");
		}

		Account recipientAccount = this.getAccountById(recipientAccountId);
		if (recipientAccountId == null || recipientAccountId == 0) {
			throw new DataValueNotFoundException("Sender AccountId does not exist.");
		}

		senderAccount.setBalance(senderAccount.getBalance() - amount);
		this.accountRepository.save(senderAccount);

		recipientAccount.setBalance(recipientAccount.getBalance() + amount);
		this.accountRepository.save(recipientAccount);

		Transaction newTransaction = new Transaction();
		newTransaction.setAmount(amount);
		newTransaction.setSenderAccount(senderAccount);
		newTransaction.setRecipientAccount(recipientAccount);
		newTransaction.setTransactionType(transactionTypes.getTransactionTypeNumber("Transfer"));
		transactionService.save(newTransaction);
		return senderAccount.getBalance() - amount;
	}
}
