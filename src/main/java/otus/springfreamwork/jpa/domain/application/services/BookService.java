package otus.springfreamwork.jpa.domain.application.services;

public interface BookService {
    String createBookByNameAndAuthorAndGenre(String boonName, String name, String surname, String genreName);

    String getAllBooks();

    String countBooks();

    String deleteBook(String name);

    String getBook(String name);

    String getBooksByAuthorNameAndSurname(String name, String surname);

    String getBooksByGenreName(String name);

    String getCommentsOnBook(String name);
}
