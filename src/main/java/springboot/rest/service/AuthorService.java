package springboot.rest.service;

import springboot.rest.model.AuthorWithBooks;
import springboot.rest.model.Author;
import springboot.rest.util.ResourceAlreadyExistsException;
import springboot.rest.util.ResourceValidationException;
import org.springframework.stereotype.Component;

@Component
public interface AuthorService {

    AuthorWithBooks getAuthorWithBooks(String firstName, String lastName) throws ResourceValidationException;

    Author getAuthor(String firstName, String lastName) throws ResourceValidationException;

    void save(Author author) throws ResourceValidationException, ResourceAlreadyExistsException;

}
