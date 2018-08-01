package otus.springfreamwork.jpa.com.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otus.springfreamwork.jpa.domain.application.services.CommentService;
import otus.springfreamwork.jpa.domain.dao.BookRepository;
import otus.springfreamwork.jpa.domain.dao.CommentRepository;
import otus.springfreamwork.jpa.domain.model.Book;
import otus.springfreamwork.jpa.domain.model.Comment;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public String createComment(String username, String commentText, String bookName) {
        Book book = bookRepository.getByName(bookName);
        String result;
        if (book == null) {
            result = "Не найдено книги, комментарий не создан";
        } else {
            Comment comment = new Comment(username, commentText);
            comment.setBooks(Collections.singleton(book));
            commentRepository.insert(comment);
            result = "Комментарий создан";
        }
        return result;
    }

    @Override
    public String getAllComments() {
        List<Comment> comments = commentRepository.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        if (comments.isEmpty()) {
            stringBuilder.append("Нет комментариев в базе");
        } else {
            stringBuilder.append("Список комментариев:");
            comments.forEach(comment -> stringBuilder.append("\n").append(comment));
        }
        return stringBuilder.toString();
    }

    @Override
    public String countComments() {
        return "Количество комментариев в базе: " + commentRepository.count();
    }

    @Override
    public String deleteUsernameComments(String usernanme) {
        List<Comment> comments = commentRepository.getByUsername(usernanme);
        String result;
        if (comments.isEmpty()) {
            result = "Не найдено комментариев для удаления";
        } else {
            commentRepository.deleteByUsername(usernanme);
            result = "Комментарии успешно удалены";
        }
        return result;
    }

    @Override
    public String getUsernameComments(String username) {
        List<Comment> comments = commentRepository.getByUsername(username);
        StringBuilder stringBuilder = new StringBuilder();
        if (comments.isEmpty()) {
            stringBuilder.append("Не найдено комментариев юзера");
        } else {
            stringBuilder.append("Список комментариев юзера:");
            comments.forEach(comment -> stringBuilder.append("\n").append(comment));
        }
        return stringBuilder.toString();
    }

}
