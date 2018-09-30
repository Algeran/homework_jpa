package otus.springfreamwork.jpa.com.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otus.springfreamwork.jpa.domain.application.services.AuthorService;
import otus.springfreamwork.jpa.domain.dao.AuthorRepository;
import otus.springfreamwork.jpa.domain.model.Author;

import java.util.List;

import static otus.springfreamwork.jpa.domain.model.Conutry.RUSSIA;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public String createAuthorByNameAndSurname(String name, String surname) {
        Author author = authorRepository.getByNameAndSurname(name, surname);
        String result;
        if (author == null) {
            author = new Author(name, surname, RUSSIA);
            authorRepository.insert(author);
            result = "Автор успешно создан";
        } else {
            result = "Автор уже в базе";
        }
        return result;
    }

    @Override
    public String getAllAuthors() {
        List<Author> authors = authorRepository.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        if (authors.isEmpty()) {
            stringBuilder.append("Нет авторов в базе");
        } else {
            stringBuilder.append("Список авторов:");
            authors.forEach(author -> stringBuilder.append("\n").append(author));
        }
        return stringBuilder.toString();
    }

    @Override
    public String countAuthors() {
        return "Количество авторов в базе: " + authorRepository.count();
    }

    @Override
    public String getAuthor(String name, String surname) {
        Author author = authorRepository.getByNameAndSurname(name, surname);
        String result;
        if (author == null) {
            result = "Не найдено автора в базе";
        } else {
            result = "Найден автор: " + author;
        }
        return result;
    }

    @Override
    public String deleteAuthor(String name, String surname) {
        Author author = authorRepository.getByNameAndSurname(name, surname);
        String result;
        if (author != null) {
            authorRepository.deleteByNameAndSurname(name, surname);
            result = "Автор успешно удален";
        } else {
            result = "Не найдено автора в базе для удаления";
        }
        return result;
    }
}
