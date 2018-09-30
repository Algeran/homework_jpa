package otus.springfreamwork.jpa.domain.application.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import otus.springfreamwork.jpa.com.application.services.GenreServiceImpl;
import otus.springfreamwork.jpa.domain.dao.GenreRepository;
import otus.springfreamwork.jpa.domain.model.Genre;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceTest {

    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    @Before
    public void init() {
        genreService = new GenreServiceImpl(genreRepository);
    }

    @Test
    public void genreServiceShouldCreateGenreByName() {
        String name = "fantasy";
        String result = genreService.createGenreByName(name);
        Genre genre = new Genre(name);

        assertEquals("Жанр успешно создан", result);
        verify(genreRepository, times(1)).insert(eq(genre));
        verify(genreRepository, times(1)).getByName(eq(name));
    }

    @Test
    public void genreServiceShouldNotCreateGenreCauseItsAlreadyInDB() {
        String name = "fantasy";
        Genre genre = new Genre(name);
        when(genreRepository.getByName(eq(name))).thenReturn(genre);
        String result = genreService.createGenreByName(name);

        assertEquals("Жанр уже в базе", result);
        verify(genreRepository, never()).insert(eq(genre));
        verify(genreRepository, times(1)).getByName(eq(name));
    }

    @Test
    public void genreServiceShouldReturnListOfGenres() {
        Genre genre = new Genre("fantasy");
        Genre genre_2 = new Genre("novel");
        List<Genre> genres = Arrays.asList(genre, genre_2);
        when(genreRepository.getAll()).thenReturn(genres);
        String expected = "Список жанров:\n" + genre + "\n" + genre_2;

        String result = genreService.getAllGenres();

        assertEquals(expected, result);
        verify(genreRepository, times(1)).getAll();
    }

    @Test
    public void genreServiceShouldReturnWarningCauseNoGenresInDB() {
        when(genreRepository.getAll()).thenReturn(Collections.emptyList());
        String expected = "Нет жанров в базе";

        String result = genreService.getAllGenres();

        assertEquals(expected, result);
        verify(genreRepository, times(1)).getAll();
    }

    @Test
    public void genreServiceShouldReturnCountMessage() {
        when(genreRepository.count()).thenReturn(2L);
        String expected = "Количество жанров в базе: 2";

        String result = genreService.countGenres();

        assertEquals(expected, result);
        verify(genreRepository, times(1)).count();
    }

    @Test
    public void genreRepositoryShouldReturnWarningCauseNoGenreInDBForDelete() {
        when(genreRepository.getByName(anyString())).thenReturn(null);
        String expected = "Не найдено жанра в базе для удаления";

        String result = genreService.deleteGenre("fantasy");

        assertEquals(expected, result);
        verify(genreRepository, times(1)).getByName(eq("fantasy"));
        verify(genreRepository, never()).deleteByName(anyString());
    }

    @Test
    public void genreRepositoryShouldReturnGenreByNameAndSurnameMessage() {
        String name = "fantasy";
        Genre genre = new Genre(name);
        when(genreRepository.getByName(eq(name))).thenReturn(genre);
        String expected = "Найден жанр: " + genre;

        String result = genreService.getGenre(name);

        assertEquals(expected, result);
        verify(genreRepository, times(1)).getByName(eq(genre.getName()));
    }

    @Test
    public void genreRepositoryShouldReturnWarningCauseNoGenreFound() {
        when(genreRepository.getByName(anyString())).thenReturn(null);
        String expected = "Не найдено жанра в базе";

        String result = genreService.getGenre("fantasy");

        assertEquals(expected, result);
        verify(genreRepository, times(1)).getByName(eq("fantasy"));
    }

    @Test
    public void genreRepositoryShouldDeleteGenreByNameAndSurname() {
        String name = "fantasy";
        Genre genre = new Genre(name);
        when(genreRepository.getByName(eq(name))).thenReturn(genre);
        String expected = "Жанр успешно удален";

        String result = genreService.deleteGenre(name);

        assertEquals(expected, result);
        verify(genreRepository, times(1)).getByName(eq(name));
        verify(genreRepository, times(1)).deleteByName(eq(name));
    }

}
