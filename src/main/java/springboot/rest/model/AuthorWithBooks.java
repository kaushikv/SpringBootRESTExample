package springboot.rest.model;

import springboot.rest.util.ResourceValidationException;
import springboot.rest.util.Utils;

import java.util.List;

public class AuthorWithBooks extends Author {
    private List<Book> books;

    public AuthorWithBooks() {
        super();
    }

    public AuthorWithBooks(Author a) {
        super(a);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        try {
            return Utils.serializeAuthorWithBooksJson(this);
        } catch (ResourceValidationException e) {
            System.out.println("Error serializing authorWithBooks " + this + e.getMessage());
            return null;
        }
    }
}
