package springboot.rest.service;

import springboot.rest.model.Author;
import springboot.rest.model.AuthorWithBooks;
import springboot.rest.model.Book;
import springboot.rest.util.ResourceAlreadyExistsException;
import springboot.rest.util.ResourceValidationException;
import springboot.rest.util.Utils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDao;
    private final BookDAO bookDao;

    public AuthorServiceImpl() {
        authorDao = new AuthorDAO();
        bookDao = new BookDAO();
    }

    @Override
    public AuthorWithBooks getAuthorWithBooks(final String firstName, final String lastName) throws ResourceValidationException {
        Author a = getAuthor(firstName, lastName);
        AuthorWithBooks ab= new AuthorWithBooks(a);
        if (a != null) {
            List<Book> books = new ArrayList<Book>();
            for (Book b : bookDao.getBooks()) {
                if (b.getAuthor().toUpperCase().equals(firstName.toUpperCase() + " " + lastName.toUpperCase())) {
                    books.add(b);
                }
            }
            ab.setBooks(books);
        }
        return ab;
    }

    @Override
    public Author getAuthor(final String firstName, final String lastName) throws ResourceValidationException {
        validateName(firstName, lastName);
        return authorDao.getAuthor(firstName, lastName);
    }

    @Override
    public void save(final Author author) throws ResourceValidationException, ResourceAlreadyExistsException {
        validate(author);
        authorDao.save(author);
    }

    private void validateName(final String firstName, final String lastName) throws ResourceValidationException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new ResourceValidationException("Missing author first name");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new ResourceValidationException("Missing author last name");
        }
    }

    private void validate(final Author author) throws ResourceValidationException {
        validateName(author.getFirstName(), author.getLastName());
        if (author.getBirthDate() != null) {
            Utils.validateDateMDY(author.getBirthDate());
        }
    }

}
