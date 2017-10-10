package springboot.rest.util;

public class ResourceValidationException extends Exception {
    public ResourceValidationException(String message) {
        super (message);
    }
    public ResourceValidationException(String message, Throwable t) {
        super (message, t);
    }
}
