package at.adridi.virtualpoints.services;

import java.util.List;

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

	private final AccountRepository accountRepository;
 
	/**
	 * Save new account
	 * @param newAccount
	 */
	public void save(Account newAccount) {
		this.accountRepository.save(newAccount);
	}
	
	/**
	 * Get certain account with the passed id
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Account getAccountById(long id) {
		return this.accountRepository.findById(id).orElseThrow(() -> new DataValueNotFoundException("Account Does Not Exist"));
	}
	
	public List<Account> getAllAccount(){
		return this.accountRepository.findAll();
	}
}

