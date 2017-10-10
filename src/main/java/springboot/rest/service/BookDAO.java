package springboot.rest.service;

import springboot.rest.model.Book;
import springboot.rest.util.ResourceAlreadyExistsException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDAO {
    private static final Map<String, Book> BOOKS = new HashMap<>();

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<Book>();
        books.addAll(BOOKS.values());
        return books;
    }

    public Book getBookByISBN(final String isbn) {
        return BOOKS.get(isbn.toUpperCase());
    }

    public void save(final Book book) throws ResourceAlreadyExistsException {
        validateDuplicate(book);
        BOOKS.put(book.getIsbn().toUpperCase(), book);
    }

    private void validateDuplicate(final Book book) throws ResourceAlreadyExistsException {
        Book existing = getBookByISBN(book.getIsbn());
        if (existing != null) {
            throw new ResourceAlreadyExistsException(
                    String.format("There already exists a book with ISBN=%s", book.getIsbn()));
        }
    }

}