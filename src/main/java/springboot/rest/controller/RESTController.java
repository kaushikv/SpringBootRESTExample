package springboot.rest.controller;

import springboot.rest.model.Author;
import springboot.rest.model.Book;
import springboot.rest.service.AuthorService;
import springboot.rest.service.BookService;
import springboot.rest.util.ResourceAlreadyExistsException;
import springboot.rest.util.ResourceValidationException;
import springboot.rest.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

@RestController ("restController")
public class RESTController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public @ResponseBody
    List<Book> getBooks() {
        return bookService.getBooks();
    }

    @RequestMapping(value = "/books/{isbn}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<Book> getBookByISBN(@PathVariable("isbn") String isbn) {
        Book book = bookService.getBookByISBN(isbn);
        book.add(linkTo(methodOn(RESTController.class).getBookByISBN(isbn)).withSelfRel());
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public @ResponseBody
    Author getAuthorWithBook(@RequestParam("books") boolean returnBooks, @RequestParam("fn") String firstName, @RequestParam("ln") String lastName) throws ResourceValidationException {
        if (returnBooks) {
            return authorService.getAuthorWithBooks(firstName, lastName);
        } else {
            return authorService.getAuthor(firstName, lastName);
        }
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void createBook(@RequestBody final String json) throws ResourceValidationException, ResourceAlreadyExistsException {
        if (json != null && json.trim().length() > 0) {
            Book b = Utils.deSerializeBookJson(json);
            bookService.save(b);
        } else {
            throw new ResourceValidationException("Missing content body");
        }
    }

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void createAuthor(@RequestBody final String json) throws ResourceValidationException, ResourceAlreadyExistsException {
        if (json != null && json.trim().length() > 0) {
            Author a = Utils.deSerializeAuthorJson(json);
            authorService.save(a);
        } else {
            throw new ResourceValidationException("Missing content body");
        }
    }

}
