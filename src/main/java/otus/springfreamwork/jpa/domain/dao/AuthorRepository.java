package otus.springfreamwork.jpa.domain.dao;

import otus.springfreamwork.jpa.domain.model.Author;

import java.util.List;

public interface AuthorRepository {

    void insert(Author author);

    Author getById(int id);

    Author getByNameAndSurname(String name, String surname);

    List<Author> getAll();

    void deleteById(int id);

    void deleteByNameAndSurname(String name, String surname);

    long count();
}
