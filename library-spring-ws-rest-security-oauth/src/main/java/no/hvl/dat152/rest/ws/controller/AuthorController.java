/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.service.AuthorService;

/**
 * 
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class AuthorController {

	@Autowired AuthorService authorService;

	@GetMapping("/authors")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Object> getAllAuthor() {
        
    List<Author> authors = authorService.findAll();
       if(authors == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	   }
		return new ResponseEntity<>(authors, HttpStatus.OK);	

	}

	@GetMapping("/authors/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Author> getAuthor(@PathVariable("id") int id) throws AuthorNotFoundException {
		Author author = authorService.findById(id);

		return new ResponseEntity<>(author, HttpStatus.OK);
	}

	@PostMapping("/authors")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Author> createAuthor(@RequestBody Author author) {

		Author nAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(nAuthor, HttpStatus.CREATED);
	}

	@PutMapping("/authors/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Author> updateAuthor(@RequestBody Author author, @PathVariable("id") int id) throws AuthorNotFoundException {
		
        Author uAuthor = authorService.updateAuthor(author);
		if(uAuthor == null || id != uAuthor.getAuthorId()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(uAuthor, HttpStatus.OK);
	}

		@DeleteMapping("/authors/{id}")
		@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Author> deleteAuthor(@PathVariable("id") Long id) throws AuthorNotFoundException {

		authorService.deleteById(id);

		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@GetMapping("/authors/{id}/books")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Object> findBooksByAuthorId(@PathVariable("id") Long id) throws AuthorNotFoundException {
		Set<Book> books = authorService.findBooksByAuthorId(id);

		return new ResponseEntity<>(books, HttpStatus.OK);
	}

}
