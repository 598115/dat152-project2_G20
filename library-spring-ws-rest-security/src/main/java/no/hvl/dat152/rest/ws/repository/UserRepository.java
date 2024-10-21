/**
 * 
 */
package no.hvl.dat152.rest.ws.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import no.hvl.dat152.rest.ws.model.User;


/**
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	Boolean existsByEmail(String email);

	@Query(value = "SELECT * FROM users WHERE userid = :id", nativeQuery=true)
    User findUserFromId(@Param("id") Long id);

}
