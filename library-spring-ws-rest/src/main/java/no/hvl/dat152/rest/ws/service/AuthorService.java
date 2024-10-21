/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.repository.AuthorRepository;
import no.hvl.dat152.rest.ws.repository.BookRepository;

/**
 * @author tdoy
 */
@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private BookRepository bookRepository;
		
	
	public Author findById(long id) throws AuthorNotFoundException {
		
		Author author = authorRepository.findById(id)
				.orElseThrow(()-> new AuthorNotFoundException("Author with the id: "+id+ "not found!"));
		
		return author;
	}

	public Author saveAuthor(Author author) {
		return authorRepository.save(author);
	}	
	
	public Author updateAuthor(Author author)  throws AuthorNotFoundException {

		Author oldAuthor = findById(author.getAuthorId());
		if(oldAuthor != null) {
			return authorRepository.save(author);
		}
        return null;
	}
	
	public List<Author> findAll() {
        return (List<Author>) authorRepository.findAll();
	}
	
	public Author deleteById(Long id) throws AuthorNotFoundException {
		
		Set<Book> books = findBooksByAuthorId(id); //Authors books must be deleted before author. FK restriction
		books.forEach(b -> bookRepository.delete(b));

		Author author = findById(id);
		authorRepository.delete(author);
		return author;
	}

	public Set<Book> findBooksByAuthorId(Long id) throws AuthorNotFoundException {
		Author author = findById(id);
		return author.getBooks();
	}
}
