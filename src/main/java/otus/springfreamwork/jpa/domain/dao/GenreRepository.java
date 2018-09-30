package otus.springfreamwork.jpa.domain.dao;

import otus.springfreamwork.jpa.domain.model.Genre;

import java.util.List;

public interface GenreRepository {

    void insert(Genre genre);

    Genre getById(int id);

    Genre getByName(String name);

    List<Genre> getAll();

    void deleteById(int id);

    void deleteByName(String name);

    long count();
}
