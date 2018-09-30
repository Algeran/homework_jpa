package otus.springfreamwork.jpa.domain.application.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import otus.springfreamwork.jpa.com.application.services.BookServiceImpl;
import otus.springfreamwork.jpa.domain.dao.AuthorRepository;
import otus.springfreamwork.jpa.domain.dao.BookRepository;
import otus.springfreamwork.jpa.domain.dao.CommentRepository;
import otus.springfreamwork.jpa.domain.dao.GenreRepository;
import otus.springfreamwork.jpa.domain.model.Author;
import otus.springfreamwork.jpa.domain.model.Book;
import otus.springfreamwork.jpa.domain.model.Comment;
import otus.springfreamwork.jpa.domain.model.Genre;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static otus.springfreamwork.jpa.domain.model.Conutry.RUSSIA;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private CommentRepository commentRepository;

    @Before
    public void init() {
        bookService = new BookServiceImpl(bookRepository, authorRepository, genreRepository, commentRepository);
    }

    @Test
    public void bookServiceShouldCreateBook() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        when(genreRepository.getByName(genre.getName())).thenReturn(genre);
        when(authorRepository.getByNameAndSurname(author.getName(), author.getSurname())).thenReturn(author);
        String result = bookService.createBookByNameAndAuthorAndGenre(book.getName(), author.getName(), author.getSurname(), genre.getName());

        assertEquals("Книга успешно создана", result);
        verify(bookRepository, times(1)).insert(eq(book));
        verify(bookRepository, times(1)).getByName(eq(book.getName()));
        verify(genreRepository, times(1)).getByName(eq(genre.getName()));
        verify(authorRepository, times(1)).getByNameAndSurname(eq(author.getName()), eq(author.getSurname()));
    }

    @Test
    public void bookServiceShouldCreateBookAndGenre() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        when(genreRepository.getByName(genre.getName())).thenReturn(null);
        when(authorRepository.getByNameAndSurname(author.getName(), author.getSurname())).thenReturn(author);
        String result = bookService.createBookByNameAndAuthorAndGenre(book.getName(), author.getName(), author.getSurname(), genre.getName());

        assertEquals("Создан жанр\nКнига успешно создана", result);
        verify(bookRepository, times(1)).insert(eq(book));
        verify(bookRepository, times(1)).getByName(eq(book.getName()));
        verify(genreRepository, times(1)).getByName(eq(genre.getName()));
        verify(authorRepository, times(1)).getByNameAndSurname(eq(author.getName()), eq(author.getSurname()));
    }

    @Test
    public void bookServiceShouldCreateBookAndAuthor() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        when(genreRepository.getByName(genre.getName())).thenReturn(genre);
        when(authorRepository.getByNameAndSurname(author.getName(), author.getSurname())).thenReturn(null);
        String result = bookService.createBookByNameAndAuthorAndGenre(book.getName(), author.getName(), author.getSurname(), genre.getName());

        assertEquals("Создан автор\nКнига успешно создана", result);
        verify(bookRepository, times(1)).insert(eq(book));
        verify(bookRepository, times(1)).getByName(eq(book.getName()));
        verify(genreRepository, times(1)).getByName(eq(genre.getName()));
        verify(authorRepository, times(1)).getByNameAndSurname(eq(author.getName()), eq(author.getSurname()));
    }

    @Test
    public void bookServiceShouldCreateBookAndAuthorAndGenre() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        when(genreRepository.getByName(genre.getName())).thenReturn(null);
        when(authorRepository.getByNameAndSurname(author.getName(), author.getSurname())).thenReturn(null);
        String result = bookService.createBookByNameAndAuthorAndGenre(book.getName(), author.getName(), author.getSurname(), genre.getName());

        assertEquals("Создан автор\nСоздан жанр\nКнига успешно создана", result);
        verify(bookRepository, times(1)).insert(eq(book));
        verify(bookRepository, times(1)).getByName(eq(book.getName()));
        verify(genreRepository, times(1)).getByName(eq(genre.getName()));
        verify(authorRepository, times(1)).getByNameAndSurname(eq(author.getName()), eq(author.getSurname()));
    }

    @Test
    public void bookServiceShouldNotCreateBookCauseItsAlreadyInDB() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        when(bookRepository.getByName(eq(book.getName()))).thenReturn(book);
        String result = bookService.createBookByNameAndAuthorAndGenre(book.getName(), author.getName(), author.getSurname(), genre.getName());

        assertEquals("Книга уже в базе", result);
        verify(bookRepository, never()).insert(eq(book));
        verify(bookRepository, times(1)).getByName(eq(book.getName()));
        verify(genreRepository, never()).getByName(eq(genre.getName()));
        verify(authorRepository, never()).getByNameAndSurname(eq(author.getName()), eq(author.getSurname()));
    }

    @Test
    public void bookServiceShouldReturnListOfBooks() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        Book book_2 = new Book("Anna Karenina", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        List<Book> books = Arrays.asList(book, book_2);
        when(bookRepository.getAll()).thenReturn(books);
        String expected = "Список книг:\n" + book + "\n" + book_2;

        String result = bookService.getAllBooks();

        assertEquals(expected, result);
        verify(bookRepository, times(1)).getAll();
    }

    @Test
    public void bookServiceShouldReturnWarningCauseNoBooksInDB() {
        when(bookRepository.getAll()).thenReturn(Collections.emptyList());
        String expected = "Нет книг в базе";

        String result = bookService.getAllBooks();

        assertEquals(expected, result);
        verify(bookRepository, times(1)).getAll();
    }

    @Test
    public void bookServiceShouldReturnCountMessage() {
        when(bookRepository.count()).thenReturn(2L);
        String expected = "Количество книг в базе: 2";

        String result = bookService.countBooks();

        assertEquals(expected, result);
        verify(bookRepository, times(1)).count();
    }

    @Test
    public void bookRepositoryShouldReturnWarningCauseNoBookInDBForDelete() {
        when(bookRepository.getByName(anyString())).thenReturn(null);
        String expected = "Не найдено книги в базе для удаления";

        String result = bookService.deleteBook("War And Piece");

        assertEquals(expected, result);
        verify(bookRepository, times(1)).getByName(eq("War And Piece"));
        verify(bookRepository, never()).deleteByName(anyString());
    }

    @Test
    public void bookRepositoryShouldReturnBookByNameAndSurnameMessage() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        when(bookRepository.getByName(eq(book.getName()))).thenReturn(book);
        String expected = "Найдена книга: " + book;

        String result = bookService.getBook(book.getName());

        assertEquals(expected, result);
        verify(bookRepository, times(1)).getByName(eq(book.getName()));
    }

    @Test
    public void bookRepositoryShouldReturnWarningCauseNoBookFound() {
        when(bookRepository.getByName(anyString())).thenReturn(null);
        String expected = "Не найдено книги в базе";

        String result = bookService.getBook("War And Piece");

        assertEquals(expected, result);
        verify(bookRepository, times(1)).getByName(eq("War And Piece"));
    }

    @Test
    public void bookRepositoryShouldDeleteBookByNameAndSurname() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        when(bookRepository.getByName(eq(book.getName()))).thenReturn(book);
        String expected = "Книга успешно удалена";

        String result = bookService.deleteBook(book.getName());

        assertEquals(expected, result);
        verify(bookRepository, times(1)).getByName(eq(book.getName()));
        verify(bookRepository, times(1)).deleteByName(eq(book.getName()));
    }

    @Test
    public void bookRepositoryShouldReturnListOfBookByAuthor() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        when(authorRepository.getByNameAndSurname(eq(author.getName()), eq(author.getSurname()))).thenReturn(author);
        when(bookRepository.getByAuthorId(author.getId())).thenReturn(Collections.singletonList(book));
        String expected = "Список книг для автора:\n" + book;

        String result = bookService.getBooksByAuthorNameAndSurname(author.getName(), author.getSurname());

        assertEquals(expected, result);
        verify(authorRepository, times(1)).getByNameAndSurname(eq(author.getName()), eq(author.getSurname()));
        verify(bookRepository, times(1)).getByAuthorId(eq(author.getId()));
    }

    @Test
    public void bookRepositoryShouldReturnOnAuthorNameAndSurnameEmptyListWarning() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        when(authorRepository.getByNameAndSurname(eq(author.getName()), eq(author.getSurname()))).thenReturn(author);
        when(bookRepository.getByAuthorId(author.getId())).thenReturn(Collections.emptyList());
        String expected = "Список книг для выбранного автора пуст";

        String result = bookService.getBooksByAuthorNameAndSurname(author.getName(), author.getSurname());

        assertEquals(expected, result);
        verify(authorRepository, times(1)).getByNameAndSurname(eq(author.getName()), eq(author.getSurname()));
        verify(bookRepository, times(1)).getByAuthorId(eq(author.getId()));
    }

    @Test
    public void bookRepositoryShouldReturnOnAuthorNameAndSurnameWarningCauseNoAuthorInDB() {
        when(authorRepository.getByNameAndSurname(eq("Leo"), eq("Tolstoy"))).thenReturn(null);
        String expected = "В базе нет такого автора";

        String result = bookService.getBooksByAuthorNameAndSurname("Leo", "Tolstoy");

        assertEquals(expected, result);
        verify(authorRepository, times(1)).getByNameAndSurname(eq("Leo"), eq("Tolstoy"));
        verify(bookRepository, never()).getByAuthorId(anyInt());
    }

    @Test
    public void bookRepositoryShouldReturnListOfBookByGenre() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        when(genreRepository.getByName(eq(genre.getName()))).thenReturn(genre);
        when(bookRepository.getByGenreId(author.getId())).thenReturn(Collections.singletonList(book));
        String expected = "Список книг для жанра:\n" + book;

        String result = bookService.getBooksByGenreName(genre.getName());

        assertEquals(expected, result);
        verify(genreRepository, times(1)).getByName(eq(genre.getName()));
        verify(bookRepository, times(1)).getByGenreId(eq(author.getId()));
    }

    @Test
    public void bookRepositoryShouldReturnOnGenreNameEmptyListWarning() {
        Genre genre = new Genre("novel");
        when(genreRepository.getByName(eq(genre.getName()))).thenReturn(genre);
        when(bookRepository.getByGenreId(genre.getId())).thenReturn(Collections.emptyList());
        String expected = "Список книг для выбранного жанра пуст";

        String result = bookService.getBooksByGenreName(genre.getName());

        assertEquals(expected, result);
        verify(genreRepository, times(1)).getByName(eq(genre.getName()));
        verify(bookRepository, times(1)).getByGenreId(eq(genre.getId()));
    }

    @Test
    public void bookRepositoryShouldReturnOnGenreNameWarningCauseNoAuthorInDB() {
        when(genreRepository.getByName(eq("novel"))).thenReturn(null);
        String expected = "В базе нет такого жанра";

        String result = bookService.getBooksByGenreName("novel");

        assertEquals(expected, result);
        verify(genreRepository, times(1)).getByName(eq("novel"));
        verify(bookRepository, never()).getByGenreId(anyInt());
    }

    @Test
    public void bookRepositoryShouldReturnListOfCommentsOnBook() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        Comment comment = new Comment("user", "so good");
        comment.setBooks(Collections.singleton(book));
        when(commentRepository.getByBookName(eq(book.getName()))).thenReturn(Collections.singletonList(comment));
        String expected = "Список комментариев для книги:\n" + comment;

        String result = bookService.getCommentsOnBook(book.getName());

        assertEquals(expected, result);
        verify(commentRepository, times(1)).getByBookName(eq(book.getName()));
    }

    @Test
    public void bookRepositoryShouldReturnMessageOfNoCommentsOnBook() {
        when(commentRepository.getByBookName(anyString())).thenReturn(Collections.emptyList());
        String expected = "Нет комментариев на книгу";

        String result = bookService.getCommentsOnBook("War And Piece");

        assertEquals(expected, result);
        verify(commentRepository, times(1)).getByBookName("War And Piece");
    }

}
