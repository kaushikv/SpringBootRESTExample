Sample Application to demonstrate REST API implementation using Spring BOOT

Build:
    mvn clean install
MainClass:
    springboot.rest.main.MainClass
Unit Tests:
    RestApplicationContextTests
    RestControllerTests

Resources:
    Author (firstName, lastName, @Optional birthdate in MM/dd/YYYY format)
    Book (ISBN, Title, Author in "firstName 'SPACE' lastName" format)

Services:
    Required for this test:
        1. Description: An API consumer should be able to create books with a Title, ISBN, and Author
           URI: /books
           usage: POST http://localhost:8080/books
           example: MainClass.createBook

        2. Description: An API consumer should be able to create authors with a first name, last name, and optional birthdate
           URI: /authors
           usage: POST http://localhost:8080/authors
           example: MainClass.createAuthor

        3. Description: An API consumer should be able to retrieve books using the ISBN
           URI: /books/{isbn}
           usage: GET http://localhost:8080/books/{isbn}
           example: MainClass.retrieveBookISBN

        4. Description: An API consumer should be able to retrieve an author with the list of books they have written
           URI: /authors/books=true&fn=<FIRST_NAME>&ln=<LAST_NAME>
           usage: GET http://localhost:8080/authors/books=true&fn=<FIRST_NAME>&ln=<LAST_NAME>
           example: MainClass.retrieveAuthorWithBooks

    Bonus for this test:
        1. Description: Expose the ability to retrieve all books in the collection
           URI: /books
           usage: GET http://localhost:8080/books
           example: MainClass.retrieveAllBooks

        2. Hypermedia example (Refer to /books/{isbn1} service implementation)

    Not done:
        1. Github integration"# SpringBootRESTExample" 
