package at.adridi.virtualpoints.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.adridi.virtualpoints.models.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

	Optional<Owner> findByEmail(String email);
	Optional<List<Owner>> findByForename(String forename);
	Optional<List<Owner>> findBySurname(String surname);

}
