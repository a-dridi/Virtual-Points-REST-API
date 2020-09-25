package at.adridi.virtualpoints.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.adridi.virtualpoints.models.Account;
import at.adridi.virtualpoints.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	Optional<Transaction> findByTransactionId(Long transactionId);
	Optional<List<Transaction>> findBySenderAccount(Account account);

}
