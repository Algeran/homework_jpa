package otus.springfreamwork.jpa.domain.application.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import otus.springfreamwork.jpa.com.application.services.AuthorServiceImpl;
import otus.springfreamwork.jpa.domain.dao.AuthorRepository;
import otus.springfreamwork.jpa.domain.model.Author;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static otus.springfreamwork.jpa.domain.model.Conutry.RUSSIA;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Before
    public void init() {
        authorService = new AuthorServiceImpl(authorRepository);
    }

    @Test
    public void authorServiceShouldCreateAuthorByNameAndSurname() {
        String name = "Leo";
        String surname = "Tolstoy";
        String result = authorService.createAuthorByNameAndSurname(name, surname);
        Author author = new Author(name, surname, RUSSIA);

        assertEquals("Автор успешно создан", result);
        verify(authorRepository, times(1)).insert(eq(author));
        verify(authorRepository, times(1)).getByNameAndSurname(eq(name), eq(surname));
    }

    @Test
    public void authorServiceShouldNotCreateAuthorCauseItsAlreadyInDB() {
        String name = "Leo";
        String surname = "Tolstoy";
        Author author = new Author(name, surname, RUSSIA);
        when(authorRepository.getByNameAndSurname(eq(name), eq(surname))).thenReturn(author);
        String result = authorService.createAuthorByNameAndSurname(name, surname);

        assertEquals("Автор уже в базе", result);
        verify(authorRepository, never()).insert(eq(author));
        verify(authorRepository, times(1)).getByNameAndSurname(eq(name), eq(surname));
    }

    @Test
    public void authorServiceShouldReturnListOfAuthors() {
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Author author_2 = new Author("Fedor", "Dostoevsky", RUSSIA);
        List<Author> authors = Arrays.asList(author, author_2);
        when(authorRepository.getAll()).thenReturn(authors);
        String expected = "Список авторов:\n" + author + "\n" + author_2;

        String result = authorService.getAllAuthors();

        assertEquals(expected, result);
        verify(authorRepository, times(1)).getAll();
    }

    @Test
    public void authorServiceShouldReturnWarningCauseNoAuthorsInDB() {
        when(authorRepository.getAll()).thenReturn(Collections.emptyList());
        String expected = "Нет авторов в базе";

        String result = authorService.getAllAuthors();

        assertEquals(expected, result);
        verify(authorRepository, times(1)).getAll();
    }

    @Test
    public void authorServiceShouldReturnCountMessage() {
        when(authorRepository.count()).thenReturn(2L);
        String expected = "Количество авторов в базе: 2";

        String result = authorService.countAuthors();

        assertEquals(expected, result);
        verify(authorRepository, times(1)).count();
    }

    @Test
    public void authorRepositoryShouldReturnWarningCauseNoAuthorInDBForDelete() {
        when(authorRepository.getByNameAndSurname(anyString(), anyString())).thenReturn(null);
        String expected = "Не найдено автора в базе для удаления";

        String result = authorService.deleteAuthor("Leo", "Tolstoy");

        assertEquals(expected, result);
        verify(authorRepository, times(1)).getByNameAndSurname(eq("Leo"), eq("Tolstoy"));
        verify(authorRepository, never()).deleteByNameAndSurname(anyString(), anyString());
    }

    @Test
    public void authorRepositoryShouldReturnAuthorByNameAndSurnameMessage() {
        String name = "Leo";
        String surname = "Tolstoy";
        Author author = new Author(name, surname, RUSSIA);
        when(authorRepository.getByNameAndSurname(eq(name), eq(surname))).thenReturn(author);
        String expected = "Найден автор: " + author;

        String result = authorService.getAuthor(name, surname);

        assertEquals(expected, result);
        verify(authorRepository, times(1)).getByNameAndSurname(eq(author.getName()), eq(author.getSurname()));
    }

    @Test
    public void authorRepositoryShouldReturnWarningCauseNoAuthorFound() {
        when(authorRepository.getByNameAndSurname(anyString(), anyString())).thenReturn(null);
        String expected = "Не найдено автора в базе";

        String result = authorService.getAuthor("Leo", "Tolstoy");

        assertEquals(expected, result);
        verify(authorRepository, times(1)).getByNameAndSurname(eq("Leo"), eq("Tolstoy"));
    }

    @Test
    public void authorRepositoryShouldDeleteAuthorByNameAndSurname() {
        String name = "Leo";
        String surname = "Tolstoy";
        Author author = new Author(name, surname, RUSSIA);
        when(authorRepository.getByNameAndSurname(eq(name), eq(surname))).thenReturn(author);
        String expected = "Автор успешно удален";

        String result = authorService.deleteAuthor(name, surname);

        assertEquals(expected, result);
        verify(authorRepository, times(1)).getByNameAndSurname(eq(name), eq(surname));
        verify(authorRepository, times(1)).deleteByNameAndSurname(eq(name), eq(surname));
    }
}