package at.adridi.virtualpoints.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.adridi.virtualpoints.DataValueNotFoundException;
import at.adridi.virtualpoints.models.Account;
import at.adridi.virtualpoints.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountService {

	@Autowired	
	private final AccountRepository accountRepository;

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
	 * Get certain account with the passed id. Throws DataValueNotFoundException if account is not available.
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
		if (accountId == null) {
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
			return true;
		}
	}

}
