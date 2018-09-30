package otus.springfreamwork.jpa.com.application.console;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import otus.springfreamwork.jpa.domain.application.services.AuthorService;
import otus.springfreamwork.jpa.domain.application.services.BookService;
import otus.springfreamwork.jpa.domain.application.services.CommentService;
import otus.springfreamwork.jpa.domain.application.services.GenreService;

@ShellComponent
public class ShellCommands {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public ShellCommands(
            AuthorService authorService,
            GenreService genreService,
            BookService bookService,
            CommentService commentService
    ) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @ShellMethod("listBooks")
    public String listBooks() {
        return bookService.getAllBooks();
    }

    @ShellMethod("listAuthors")
    public String listAuthors() {
        return authorService.getAllAuthors();
    }

    @ShellMethod("listGenres")
    public String listGenres() {
        return genreService.getAllGenres();
    }

    @ShellMethod("listComments")
    public String listComments() {
        return commentService.getAllComments();
    }

    @ShellMethod("countBooks")
    public String countBooks() {
        return bookService.countBooks();
    }

    @ShellMethod("countAuthors")
    public String countAuthors() {
        return authorService.countAuthors();
    }

    @ShellMethod("countGenres")
    public String countGenres() {
        return genreService.countGenres();
    }

    @ShellMethod("countComments")
    public String countComments() {
        return commentService.countComments();
    }

    @ShellMethod("getBook")
    public String getBook(@ShellOption String name) {
        return bookService.getBook(name);
    }

    @ShellMethod("geAuthorBooks")
    public String getAuthorBooks(
            @ShellOption String name,
            @ShellOption String surname
    ) {
        return bookService.getBooksByAuthorNameAndSurname(name, surname);
    }

    @ShellMethod("getGenreBooks")
    public String getGenreBooks(
            @ShellOption String name
    ) {
        return bookService.getBooksByGenreName(name);
    }

    @ShellMethod("getBookComments")
    public String getBookComments(
            @ShellOption String name
    ) {
        return bookService.getCommentsOnBook(name);
    }

    @ShellMethod("getAuthor")
    public String getAuthor(
            @ShellOption String name,
            @ShellOption String surname
    )
    {
        return authorService.getAuthor(name, surname);
    }

    @ShellMethod("getGenre")
    public String getGenre(@ShellOption String name) {
        return genreService.getGenre(name);
    }

    @ShellMethod("getComment")
    public String getComments(@ShellOption String username) {
        return commentService.getUsernameComments(username);
    }

    @ShellMethod("createAuthor")
    public String createAuthor(
            @ShellOption String name,
            @ShellOption String surname
    ) {
        return authorService.createAuthorByNameAndSurname(name, surname);
    }

    @ShellMethod("createGenre")
    public String createGenre(@ShellOption String name) {
        return genreService.createGenreByName(name);
    }

    @ShellMethod("createBook")
    public String createBook(
            @ShellOption String name,
            @ShellOption String authorName,
            @ShellOption String authorSurname,
            @ShellOption String genreName
    ) {
        return bookService.createBookByNameAndAuthorAndGenre(name, authorName, authorSurname, genreName);
    }

    @ShellMethod("createComment")
    public String createComment(
            @ShellOption String username,
            @ShellOption String commentText,
            @ShellOption String bookName
    ) {
        return commentService.createComment(username, commentText, bookName);
    }

    @ShellMethod("deleteAuthor")
    public String deleteAuthor(
            @ShellOption String name,
            @ShellOption String surname
    ) {
        return authorService.deleteAuthor(name, surname);
    }

    @ShellMethod("deleteGenre")
    public String deleteGenre(
            @ShellOption String name
    ) {
        return genreService.deleteGenre(name);
    }

    @ShellMethod("deleteBook")
    public String bookDelete(
            @ShellOption String name
    ) {
        return bookService.deleteBook(name);
    }
}
