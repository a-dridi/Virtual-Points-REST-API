package at.adridi.virtualpoints.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.adridi.virtualpoints.models.AccountType;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
	
	Optional<AccountType> findByAccountTypeId(Long accountTypeId);

}
