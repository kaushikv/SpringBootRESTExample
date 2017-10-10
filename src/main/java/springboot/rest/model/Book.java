package springboot.rest.model;

import springboot.rest.util.ResourceValidationException;
import springboot.rest.util.Utils;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.ResourceSupport;

public class Book extends ResourceSupport {
    private String title;
    private String isbn;
    private String author;

    @JsonCreator
    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        try {
            return Utils.serializeBookJson(this);
        } catch (ResourceValidationException e) {
            System.out.println("Error serializing book " + this + e.getMessage());
            return null;
        }
    }
}
