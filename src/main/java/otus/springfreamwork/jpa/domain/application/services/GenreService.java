package otus.springfreamwork.jpa.domain.application.services;

public interface GenreService {

    String createGenreByName(String name);

    String getAllGenres();

    String countGenres();

    String deleteGenre(String name);

    String getGenre(String name);
}
