package springboot.rest.model;

import springboot.rest.util.ResourceValidationException;
import springboot.rest.util.Utils;

public class Author {
    private String firstName;
    private String lastName;
    private String birthDate;

    public Author() {

    }

    public Author(Author a) {
        if (a != null) {
            firstName = a.firstName;
            lastName = a.lastName;
            birthDate = a.birthDate;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        try {
            return Utils.serializeAuthorJson(this);
        } catch (ResourceValidationException e) {
            System.out.println("Error serializing author " + this + e.getMessage());
            return null;
        }
    }

}
