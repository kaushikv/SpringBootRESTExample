package springboot.rest.service;

import springboot.rest.model.Book;
import springboot.rest.util.ResourceAlreadyExistsException;
import springboot.rest.util.ResourceValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookService {

    Book getBookByISBN(String isbn);

    List<Book> getBooks();

    void save(Book book) throws ResourceValidationException, ResourceAlreadyExistsException;

}
