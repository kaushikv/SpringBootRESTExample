package springboot.rest.main;

import springboot.rest.model.AuthorWithBooks;
import springboot.rest.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component("restClient")
public class RESTClient {
    @Autowired
    private RestTemplate restTemplate;

    public String createBook(String isbn, String title, String author) {
        Map<String, String> kvMap = new HashMap<String, String>();
        kvMap.put("isbn", isbn);
        kvMap.put("title", title);
        kvMap.put("author", author);

        String json = buildJson(kvMap);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/books", json, String.class);
            return response.getStatusCode().toString();
        } catch (HttpStatusCodeException e) {
            return e.getStatusCode() + ":" + e.getResponseBodyAsString();
        }
    }

    public String createAuthor(String firstName, String lastName, String birthdate) {
        Map<String, String> kvMap = new HashMap<String, String>();
        kvMap.put("firstName", firstName);
        kvMap.put("lastName", lastName);
        kvMap.put("birthDate", birthdate);

        String json = buildJson(kvMap);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/authors", json, String.class);
            return response.getStatusCode().toString();
        } catch (HttpStatusCodeException e) {
            return e.getStatusCode() + ":" + e.getResponseBodyAsString();
        }
    }

    private String buildJson(Map<String, String> kvMap) {
        String currentWrapperQuote;
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int i = 0;
        for (String key : kvMap.keySet()) {
            if (i != 0) {
                sb.append(", ");
            }
            i++;
            String value = kvMap.get(key);
            currentWrapperQuote = getWrapperQuote(value);
            sb.append("\"").append(key).append("\":").append(currentWrapperQuote).append(value).append(currentWrapperQuote);
        }
        sb.append("}");
        return sb.toString();
    }

    private String getWrapperQuote(String value) {
        String defaultValueWrapperQuote = "\"";
        String defaultNullWrapperQuote = "";

        String currentWrapperQuote;
        if (value != null) {
            currentWrapperQuote = defaultValueWrapperQuote;
        } else {
            currentWrapperQuote = defaultNullWrapperQuote;
        }
        return currentWrapperQuote;
    }

    public String getAllBooks() {
        Book[] books = restTemplate.getForObject("http://localhost:8080/books", Book[].class);
        StringBuilder sb = new StringBuilder();
        for (Book b : books) {
            sb.append(b).append("\n");
        }
        return sb.toString();
    }

    public String getBookByISBN(String isbn) {
        Map<String, String> uriParams = new HashMap<String, String>();
        uriParams.put("isbn", isbn);

        Book book = restTemplate.getForObject("http://localhost:8080/books/{isbn}", Book.class, uriParams);
        
        return book.toString();
    }

    public String getAuthorWithBooks(String firstName, String lastName) {
        Map<String, String> uriParams = new HashMap<String, String>();
        uriParams.put("returnBooks", "true");
        uriParams.put("firstName", firstName);
        uriParams.put("lastName", lastName);

        AuthorWithBooks author = restTemplate.getForObject("http://localhost:8080/authors?books={returnBooks}&fn={firstName}&ln={lastName}", AuthorWithBooks.class, uriParams);
        return author.toString();
    }
}
