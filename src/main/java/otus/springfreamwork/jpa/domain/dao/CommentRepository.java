package otus.springfreamwork.jpa.domain.dao;

import otus.springfreamwork.jpa.domain.model.Comment;

import java.util.List;

public interface CommentRepository {

    void insert(Comment comment);

    Comment getById(int id);

    List<Comment> getByUsername(String username);

    List<Comment> getAll();

    long count();

    void deleteById(int id);

    void deleteByUsername(String username);

    List<Comment> getByBookName(String bookName);

}
