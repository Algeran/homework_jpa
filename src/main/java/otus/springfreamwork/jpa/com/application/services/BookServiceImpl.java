package otus.springfreamwork.jpa.com.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otus.springfreamwork.jpa.domain.application.services.BookService;
import otus.springfreamwork.jpa.domain.dao.AuthorRepository;
import otus.springfreamwork.jpa.domain.dao.BookRepository;
import otus.springfreamwork.jpa.domain.dao.CommentRepository;
import otus.springfreamwork.jpa.domain.dao.GenreRepository;
import otus.springfreamwork.jpa.domain.model.Author;
import otus.springfreamwork.jpa.domain.model.Book;
import otus.springfreamwork.jpa.domain.model.Comment;
import otus.springfreamwork.jpa.domain.model.Genre;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static otus.springfreamwork.jpa.domain.model.Conutry.RUSSIA;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BookServiceImpl(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            GenreRepository genreRepository,
            CommentRepository commentRepository
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public String createBookByNameAndAuthorAndGenre(String bookName, String name, String surname, String genreName) {

        Book book = bookRepository.getByName(bookName);
        if (book != null) {
            return "Книга уже в базе";
        }

        StringBuilder result = new StringBuilder();
        Author author = authorRepository.getByNameAndSurname(name, surname);
        if (author == null) {
            author = new Author(name, surname, RUSSIA);
            result.append("Создан автор").append("\n");
        }

        Genre genre = genreRepository.getByName(genreName);
        if (genre == null) {
            genre = new Genre(genreName);
            result.append("Создан жанр").append("\n");
        }

        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        book = new Book(bookName, new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        bookRepository.insert(book);
        return result.append("Книга успешно создана").toString();
    }

    @Override
    public String getAllBooks() {
        List<Book> books = bookRepository.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        if (books.isEmpty()) {
            stringBuilder.append("Нет книг в базе");
        } else {
            stringBuilder.append("Список книг:");
            books.forEach(book -> stringBuilder.append("\n").append(book));
        }
        return stringBuilder.toString();
    }

    @Override
    public String countBooks() {
        return "Количество книг в базе: " + bookRepository.count();
    }

    @Override
    public String deleteBook(String name) {
        Book book = bookRepository.getByName(name);
        String result;
        if (book != null) {
            bookRepository.deleteByName(name);
            result = "Книга успешно удалена";
        } else {
            result = "Не найдено книги в базе для удаления";
        }
        return result;
    }

    @Override
    public String getBook(String name) {
        Book book = bookRepository.getByName(name);
        String result;
        if (book == null) {
            result = "Не найдено книги в базе";
        } else {
            result = "Найдена книга: " + book;
        }
        return result;
    }

    @Override
    public String getBooksByAuthorNameAndSurname(String name, String surname) {
        StringBuilder stringBuilder = new StringBuilder();
        Author author = authorRepository.getByNameAndSurname(name, surname);
        if (author == null) {
            stringBuilder.append("В базе нет такого автора");
        } else {
            List<Book> books = bookRepository.getByAuthorId(author.getId());
            if (books.isEmpty()) {
                stringBuilder.append("Список книг для выбранного автора пуст");
            } else {
                stringBuilder.append("Список книг для автора:");
                books.forEach(book -> stringBuilder.append("\n").append(book));
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String getBooksByGenreName(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        Genre genre = genreRepository.getByName(name);
        if (genre == null) {
            stringBuilder.append("В базе нет такого жанра");
        } else {
            List<Book> books = bookRepository.getByGenreId(genre.getId());
            if (books.isEmpty()) {
                stringBuilder.append("Список книг для выбранного жанра пуст");
            } else {
                stringBuilder.append("Список книг для жанра:");
                books.forEach(book -> stringBuilder.append("\n").append(book));
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String getCommentsOnBook(String name) {
        List<Comment> comments = commentRepository.getByBookName(name);
        StringBuilder stringBuilder = new StringBuilder();
        if (comments.isEmpty()) {
            stringBuilder.append("Нет комментариев на книгу");
        } else {
            stringBuilder.append("Список комментариев для книги:");
            comments.forEach(comment -> stringBuilder.append("\n").append(comment));
        }
        return stringBuilder.toString();
    }
}
