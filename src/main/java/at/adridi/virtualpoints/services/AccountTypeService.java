package at.adridi.virtualpoints.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.adridi.virtualpoints.DataValueNotFoundException;
import at.adridi.virtualpoints.models.Account;
import at.adridi.virtualpoints.models.AccountType;
import at.adridi.virtualpoints.repositories.AccountTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountTypeService {

	@Autowired
	private final AccountTypeRepository accountTypeRepository;

	/**
	 * Save new accounttype.
	 * 
	 * @param newAccountType
	 * @return true if successful
	 */
	public boolean save(AccountType newAccountType) {
		if (newAccountType == null) {
			return false;
		}
		if (this.accountTypeRepository.save(newAccountType) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get certain accounttype with the passed id. Throws DataValueNotFoundException if account type is not available. 
	 * @param id
	 * @return 
	 */
	public AccountType getAccountTypeById(long id) {
		return this.accountTypeRepository.findByAccountTypeId(id).orElseThrow(() -> new DataValueNotFoundException("AccountType Does Not Exist"));
	}
	
	/**
	 * Get a List of all saved AccountType objects. 
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<AccountType> getAllAccountType() {
		return this.accountTypeRepository.findAll();
	}

	/**
	 * Update Accounttype with the values passed in the object updatedAccountType.
	 * 
	 * @param updatedAccountType
	 * @return true if successful
	 */
	public boolean update(AccountType updatedAccountType) {
		if (updatedAccountType == null) {
			return false;
		}
		AccountType accountType = this.getAccountTypeById(updatedAccountType.getAccountTypeId());
		if (accountType != null) {
			accountType.setAccountTypeId(updatedAccountType.getAccountTypeId());
			accountType.setTitle(updatedAccountType.getTitle());
			accountType.setWidthdrawLimit(updatedAccountType.getWidthdrawLimit());
			if (this.accountTypeRepository.save(accountType) != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Delete an existing AccountType
	 * 
	 * @param accountTypeId
	 * @return true if successful
	 */
	public boolean deleteById(Long accountTypeId) {
		if (accountTypeId == null || accountTypeId==0) {
			return false;
		}
		AccountType accountType = this.getAccountTypeById(accountTypeId);
		if (accountType != null) {
			this.accountTypeRepository.delete(accountType);
			if (this.getAccountTypeById(accountType.getAccountTypeId()) != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
