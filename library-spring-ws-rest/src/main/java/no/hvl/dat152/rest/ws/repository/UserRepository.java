/**
 * 
 */
package no.hvl.dat152.rest.ws.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.model.User;

/**
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {


}
