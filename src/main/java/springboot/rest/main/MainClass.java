package springboot.rest.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@SpringBootConfiguration
@ComponentScan("springboot.rest")
public class MainClass {
    
	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(MainClass.class, args);
        // ApplicationContext context =
           //     new ClassPathXmlApplicationContext(new String[] {"context.xml"});

		RESTClient restClient = (RESTClient) context.getBean("restClient");

		createBook(restClient);

        createAuthor(restClient);

		retrieveBookISBN(restClient);

        retrieveAuthorWithBooks(restClient);

        retrieveAllBooks(restClient);

        System.out.println("Success");
	}

    private static void createBook(RESTClient restClient) {
        System.out.println("createBook return:" + restClient.createBook("isbn1", "title1", "author1"));
    }

    private static void createAuthor(RESTClient restClient) {
        System.out.println("createAuthor return:" + restClient.createAuthor("af1", "al1", "12/12/2020"));
	}

    private static void retrieveBookISBN(RESTClient restClient) {
        System.out.println("retrieveBookISBN return:" + restClient.getBookByISBN("isbn1"));
    }

    private static void retrieveAuthorWithBooks(RESTClient restClient) {
        System.out.println("retrieveAuthorWithBooks return:" + restClient.getAuthorWithBooks("af1", "al1"));
    }

    private static void retrieveAllBooks(RESTClient restClient) {
        System.out.println("retrieveAuthorWithBooks return:" + restClient.getAllBooks());
    }

    @Bean
    public RestTemplate geRestTemplate() {
        return new RestTemplate();
    }
}
