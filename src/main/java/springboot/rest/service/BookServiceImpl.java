package springboot.rest.service;

import springboot.rest.model.Book;
import springboot.rest.util.ResourceAlreadyExistsException;
import springboot.rest.util.ResourceValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookServiceImpl implements BookService {

    private final BookDAO bookDao;

    public BookServiceImpl() {
        bookDao = new BookDAO();
    }

    @Override
    public Book getBookByISBN(final String isbn) {
        return bookDao.getBookByISBN(isbn);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public void save(final Book book) throws ResourceValidationException, ResourceAlreadyExistsException {
        validate(book);
        bookDao.save(book);
    }

    private void validate(final Book book) throws ResourceValidationException {
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new ResourceValidationException("Missing book author");
        }
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new ResourceValidationException("Missing book ISBN");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new ResourceValidationException("Missing book Title");
        }
    }

}
