package at.adridi.virtualpoints.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.adridi.virtualpoints.models.Account;
import at.adridi.virtualpoints.models.Owner;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByAccountId(Long accountId);
	Optional<List<Account>> findByOwner(Owner owner);

}
