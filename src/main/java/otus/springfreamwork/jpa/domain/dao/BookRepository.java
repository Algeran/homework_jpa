package otus.springfreamwork.jpa.domain.dao;

import otus.springfreamwork.jpa.domain.model.Book;

import java.util.List;

public interface BookRepository {

    void insert(Book book);

    Book getById(int id);

    Book getByName(String name);

    List<Book> getAll();

    long count();

    void deleteById(int id);

    void deleteByName(String name);

    List<Book> getByAuthorId(int authorId);

    List<Book> getByGenreId(int genreId);
}
