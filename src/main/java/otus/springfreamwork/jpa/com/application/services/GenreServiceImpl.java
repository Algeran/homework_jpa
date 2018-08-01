package otus.springfreamwork.jpa.com.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otus.springfreamwork.jpa.domain.application.services.GenreService;
import otus.springfreamwork.jpa.domain.dao.GenreRepository;
import otus.springfreamwork.jpa.domain.model.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public String createGenreByName(String name) {
        Genre genre = genreRepository.getByName(name);
        String result;
        if (genre == null) {
            genre = new Genre(name);
            genreRepository.insert(genre);
            result = "Жанр успешно создан";
        } else {
            result = "Жанр уже в базе";
        }
        return result;
    }

    @Override
    public String getAllGenres() {
        List<Genre> genres = genreRepository.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        if (genres.isEmpty()) {
            stringBuilder.append("Нет жанров в базе");
        } else {
            stringBuilder.append("Список жанров:");
            genres.forEach(genre -> stringBuilder.append("\n").append(genre));
        }
        return stringBuilder.toString();
    }

    @Override
    public String countGenres() {
        return "Количество жанров в базе: " + genreRepository.count();
    }

    @Override
    public String deleteGenre(String name) {
        Genre genre = genreRepository.getByName(name);
        String result;
        if (genre != null) {
            genreRepository.deleteByName(name);
            result = "Жанр успешно удален";
        } else {
            result = "Не найдено жанра в базе для удаления";
        }
        return result;
    }

    @Override
    public String getGenre(String name) {
        Genre genre = genreRepository.getByName(name);
        String result;
        if (genre == null) {
            result = "Не найдено жанра в базе";
        } else {
            result = "Найден жанр: " + genre;
        }
        return result;
    }
}
