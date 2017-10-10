package springboot.rest.util;

import springboot.rest.model.Author;
import springboot.rest.model.AuthorWithBooks;
import springboot.rest.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final String FORMAT_MDY = "MM/dd/yyyy";

    public static void validateDateMDY(String birthDate) throws ResourceValidationException {
        DateFormat formatter = new SimpleDateFormat(FORMAT_MDY);
        formatter.setLenient(false);
        try {
            Date date = formatter.parse(birthDate);
        } catch (ParseException e) {
            String message = String.format("Invalid author birth date. Required format %s. Actual %s", FORMAT_MDY, birthDate);
            throw new ResourceValidationException(message, e);
        }
    }

    public static Book deSerializeBookJson(String json) throws ResourceValidationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Book b = mapper.readValue(json, Book.class);
            return b;
        } catch (IOException e) {
            throw new ResourceValidationException("Invalid content String for author " + json, e);
        }
    }

    public static String serializeBookJson(Book b) throws ResourceValidationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(b);
            return json;
        } catch (JsonProcessingException e) {
            throw new ResourceValidationException("Unable to return string for book " + b, e);
        }
    }

    public static Author deSerializeAuthorJson(String json) throws ResourceValidationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Author a = mapper.readValue(json, Author.class);
            return a;
        } catch (IOException e) {
            throw new ResourceValidationException("Invalid content String for author " + json, e);
        }
    }

    public static String serializeAuthorJson(Author a) throws ResourceValidationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(a);
            return json;
        } catch (JsonProcessingException e) {
            throw new ResourceValidationException("Unable to return string for Author " + a, e);
        }
    }

    public static String serializeAuthorWithBooksJson(AuthorWithBooks ab) throws ResourceValidationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(ab);
            return json;
        } catch (JsonProcessingException e) {
            throw new ResourceValidationException("Unable to return string for AuthorWithBooks " + ab, e);
        }
    }
}
