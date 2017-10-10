package springboot.rest.test;

import springboot.rest.controller.RESTController;
import springboot.rest.model.Author;
import springboot.rest.model.AuthorWithBooks;
import springboot.rest.model.Book;
import springboot.rest.service.AuthorService;
import springboot.rest.service.BookService;
import springboot.rest.util.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration({"/context.xml"})
public class RestControllerTests {

    @Mock
    private
    BookService bookService;

    @Mock
    private
    AuthorService authorService;

    @InjectMocks
    private
    RESTController restController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
    }

    @Test
    public void testGetBookByISBN() throws Exception {
        Book b = new Book();
        b.setTitle("t");
        b.setIsbn("i");
        b.setAuthor("a");
        BDDMockito.given(bookService.getBookByISBN("isbn1")).willReturn(b);
        mockMvc.perform(get("/books/isbn1"))
                .andExpect(status().isOk())
                .andExpect(content().string(b.toString()));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Book b1 = new Book();
        b1.setTitle("t1");
        b1.setIsbn("i1");
        b1.setAuthor("a1");
        Book b2 = new Book();
        b2.setTitle("t2");
        b2.setIsbn("i2");
        b2.setAuthor("a2");
        List<Book> bList = new ArrayList<Book>();
        bList.add(b1);
        bList.add(b2);
        BDDMockito.given(bookService.getBooks()).willReturn(bList);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(bList.toString().replaceAll(" ", "")));
        ;
    }

    @Test
    public void testGetAuthorWithBooks() throws Exception {
        AuthorWithBooks a = new AuthorWithBooks();
        a.setFirstName("fn");
        a.setLastName("ln");
        Book b1 = new Book();
        b1.setTitle("t1");
        b1.setIsbn("i1");
        b1.setAuthor("xx");
        Book b2 = new Book();
        b2.setTitle("t2");
        b2.setIsbn("i2");
        b2.setAuthor("xx");
        List<Book> bList = new ArrayList<Book>();
        bList.add(b1);
        bList.add(b2);
        a.setBooks(bList);
        BDDMockito.given(authorService.getAuthorWithBooks("ABC", "XYZ")).willReturn(a);

        mockMvc.perform(get("/authors?books=true&fn=ABC&ln=XYZ"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(a.toString().replaceAll(" ", "")));
        ;
    }

    @Test
    public void createBookFailsWhenInvalidDataPosted() throws Exception {
        // POST no data to the form (i.e. an invalid POST)
        mockMvc.perform(post("/books")).andExpect(status().is4xxClientError());
    }

    @Test
    public void createAuthorFailsWhenInvalidDataPosted() throws Exception {
        // POST no data to the form (i.e. an invalid POST)
        mockMvc.perform(post("/authors")).andExpect(status().is4xxClientError());
    }

    @Test
    public void createBookSuccessful() throws Exception {
        doNothing().when(bookService).save(isA(Book.class));//
        Book b = new Book();
        b.setIsbn("isbn1");
        b.setTitle("title1");
        b.setAuthor("fn1 ln1");
        String json = Utils.serializeBookJson(b);
        mockMvc.perform(
                post("/books").content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void createAuthorSuccessful() throws Exception {
        doNothing().when(authorService).save(isA(Author.class));//
        Author a = new Author();
        a.setFirstName("fn");
        a.setLastName("ln");
        String json = Utils.serializeAuthorJson(a);
        mockMvc.perform(
                post("/authors").content(json)
        )
                .andExpect(status().isOk());
    }
}
