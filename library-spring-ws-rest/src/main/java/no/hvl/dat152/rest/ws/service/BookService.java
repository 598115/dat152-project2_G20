/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.BookNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UpdateBookFailedException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.repository.BookRepository;

/**
 * @author tdoy
 */
@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	
	public Book saveBook(Book book) {
		
		return bookRepository.save(book);
		
	}
	
	public List<Book> findAll(){
		
		return (List<Book>) bookRepository.findAll();
		
	}
	
	
	public Book findByISBN(String isbn) throws BookNotFoundException {
		
		Book book = bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new BookNotFoundException("Book with isbn = "+isbn+" not found!"));
		
		return book;
	}

	public Book updateBook(Book book) {
		
		Book ubook = null;
		try{
		  Book oldbook = findByISBN(book.getIsbn());
		  if(oldbook != null) {
			  ubook = bookRepository.save(book);
		  }
		  }
		  catch(Exception e){
			 e.printStackTrace();
		  }
		  return ubook;		
	  }

	  public void deleteByISBN(String isbn) {
		
		try{
		Book dbook = findByISBN(isbn);
		bookRepository.delete(dbook);
		}
		catch(Exception e){
           e.printStackTrace();
		}
	}

	  public List<Book> findAllPaginate(Pageable page) {
		return findAllPaginate(page);
	  }

	  public Set<Author> findAuthorsOfBookByISBN(String isbn) throws BookNotFoundException {
		Book book = findByISBN(isbn);
        return book.getAuthors();
	  }

	  public void deleteById(long id) {
		
		try{
			Book dbook = bookRepository.findBookById(id);
			bookRepository.delete(dbook);
			}
			catch(Exception e){
			   e.printStackTrace();
			}
	  }
	
}
