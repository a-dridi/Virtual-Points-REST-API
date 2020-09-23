package at.adridi.virtualpoints.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.adridi.virtualpoints.DataValueNotFoundException;
import at.adridi.virtualpoints.models.Account;
import at.adridi.virtualpoints.models.AccountType;
import at.adridi.virtualpoints.models.Owner;
import at.adridi.virtualpoints.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
public class OwnerService {

	@Autowired
	private OwnerRepository ownerRepository;

	/*
	 * Save new Owner.
	 * 
	 * @param newOwner
	 * 
	 * @return true if successful
	 */
	public boolean save(Owner newOwner) {
		if (newOwner == null) {
			return false;
		}
		if (this.ownerRepository.save(newOwner) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get certain Owner with the passed id. Throws DataValueNotFoundException if Owner is not available. 
	 * @param id
	 * @return 
	 */
	public Owner getOwnerById(long id) {
		return this.ownerRepository.findByOwnerId(id).orElseThrow(() -> new DataValueNotFoundException("Owner Does Not Exist"));
	}
	
	/**
	 * Get certain owner with the passed email
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Owner getOwnerByEmail(String email) {
		return this.ownerRepository.findByEmail(email)
				.orElseThrow(() -> new DataValueNotFoundException("Owner Does Not Exist!"));
	}

	@Transactional(readOnly = true)
	public List<Owner> getOwnerByForename(String forename) {
		return this.ownerRepository.findByForename(forename)
				.orElseThrow(() -> new DataValueNotFoundException("Owner Does Not Exist!"));
	}

	@Transactional(readOnly = true)
	public List<Owner> getOwnerBySurname(String surname) {
		return this.ownerRepository.findBySurname(surname)
				.orElseThrow(() -> new DataValueNotFoundException("Owner Does Not Exist!"));
	}
	
	/**
	 * Get a List of all saved owner objects
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Owner> getAllOwner() {
		return this.ownerRepository.findAll();
	}

	/**
	 * Update owner with the values passed in the object updateOwner
	 * 
	 * @param updatedOwner
	 * @return true if successful
	 */
	public boolean update(Owner updatedOwner) {
		if (updatedOwner == null) {
			return false;
		}
		Owner owner = this.getOwnerById(updatedOwner.getOwnerId());
		if (owner != null) {
			owner.setAccounts(updatedOwner.getAccounts());
			owner.setEmail(updatedOwner.getEmail());
			owner.setForename(updatedOwner.getForename());
			owner.setSurname(updatedOwner.getSurname());
			owner.setPassword(updatedOwner.getPassword());
			if (this.ownerRepository.save(owner) != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Delete an existing owner
	 * 
	 * @param ownerId
	 * @return true if successful
	 */
	public boolean deleteById(Long ownerId) {
		if (ownerId == null) {
			return false;
		}
		Owner owner = this.getOwnerById(ownerId);
		if (owner != null) {
			this.ownerRepository.delete(owner);
			if (this.getOwnerById(owner.getOwnerId()) != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
