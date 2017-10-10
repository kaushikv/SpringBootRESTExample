package springboot.rest.service;

import springboot.rest.model.Author;
import springboot.rest.util.ResourceAlreadyExistsException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthorDAO {
    private static final Map<String, Author> AUTHORS = new HashMap<>();

    public Collection<Author> getAuthors() {
        return AUTHORS.values();
    }

    public Author getAuthor(String firstName, String lastName) {
        return AUTHORS.get(firstName.toUpperCase() + ":" + lastName.toUpperCase());
    }

    public void save(final Author author) throws ResourceAlreadyExistsException {
        validateDuplicate(author);
        AUTHORS.put(author.getFirstName().toUpperCase() + ":" + author.getLastName().toUpperCase(), author);
    }

    private void validateDuplicate(final Author author) throws ResourceAlreadyExistsException {
        Author existing = getAuthor(author.getFirstName(), author.getLastName());
        if (existing != null) {
            throw new ResourceAlreadyExistsException(
                    String.format("There already exists an author with name=%s %s", author.getFirstName(), author.getLastName()));
        }
    }
}