package otus.springfreamwork.jpa.domain.application.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import otus.springfreamwork.jpa.com.application.services.CommentServiceImpl;
import otus.springfreamwork.jpa.domain.dao.BookRepository;
import otus.springfreamwork.jpa.domain.dao.CommentRepository;
import otus.springfreamwork.jpa.domain.model.Author;
import otus.springfreamwork.jpa.domain.model.Book;
import otus.springfreamwork.jpa.domain.model.Comment;
import otus.springfreamwork.jpa.domain.model.Genre;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static otus.springfreamwork.jpa.domain.model.Conutry.RUSSIA;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookRepository bookRepository;

    @Before
    public void init() {
        commentService = new CommentServiceImpl(commentRepository, bookRepository);
    }

    @Test
    public void commentServiceShouldCreateCommentByNameAndSurusername() {
        String username = "user";
        String commentText = "so good";
        Author author = new Author("Leo", "Tolstoy", RUSSIA);
        Genre genre = new Genre("novel");
        Map<Integer, String> parts = Collections.singletonMap(1, "partOne");
        Book book = new Book("War And Piece", new Date(2018, 4, 10), parts, Collections.singleton(author), genre);
        Comment comment = new Comment(username, commentText);
        comment.setBooks(Collections.singleton(book));
        when(bookRepository.getByName(eq(book.getName()))).thenReturn(book);

        String result = commentService.createComment(comment.getUsername(), comment.getComment(), book.getName());

        assertEquals("Комментарий создан", result);
        verify(commentRepository, times(1)).insert(eq(comment));
        verify(bookRepository, times(1)).getByName(eq(book.getName()));
    }

    @Test
    public void commentServiceShouldNotCreateCommentCauseNoBookInDB() {
        when(bookRepository.getByName(anyString())).thenReturn(null);

        String result = commentService.createComment("username", "comment text", "War And Piece");

        assertEquals("Не найдено книги, комментарий не создан", result);
        verify(commentRepository, never()).insert(any());
        verify(bookRepository, times(1)).getByName(eq("War And Piece"));
    }

    @Test
    public void commentServiceShouldReturnListOfComments() {
        Comment comment = new Comment("usern", "so good");
        Comment comment_2 = new Comment("Fedor", "Dostoevsky");
        List<Comment> comments = Arrays.asList(comment, comment_2);
        when(commentRepository.getAll()).thenReturn(comments);
        String expected = "Список комментариев:\n" + comment + "\n" + comment_2;

        String result = commentService.getAllComments();

        assertEquals(expected, result);
        verify(commentRepository, times(1)).getAll();
    }

    @Test
    public void commentServiceShouldReturnWarningCauseNoCommentsInDB() {
        when(commentRepository.getAll()).thenReturn(Collections.emptyList());
        String expected = "Нет комментариев в базе";

        String result = commentService.getAllComments();

        assertEquals(expected, result);
        verify(commentRepository, times(1)).getAll();
    }

    @Test
    public void commentServiceShouldReturnCountMessage() {
        when(commentRepository.count()).thenReturn(2L);
        String expected = "Количество комментариев в базе: 2";

        String result = commentService.countComments();

        assertEquals(expected, result);
        verify(commentRepository, times(1)).count();
    }

    @Test
    public void commentRepositoryShouldReturnWarningCauseNoCommentInDBForDelete() {
        when(commentRepository.getByUsername(anyString())).thenReturn(Collections.emptyList());
        String expected = "Не найдено комментариев для удаления";

        String result = commentService.deleteUsernameComments("username");

        assertEquals(expected, result);
        verify(commentRepository, times(1)).getByUsername(eq("username"));
        verify(commentRepository, never()).deleteByUsername(anyString());
    }

    @Test
    public void commentRepositoryShouldReturnCommentByUsernameMessage() {
        String username = "usern";
        String commentText = "so good";
        Comment comment = new Comment(username, commentText);
        when(commentRepository.getByUsername(eq(username))).thenReturn(Collections.singletonList(comment));
        String expected = "Список комментариев юзера:\n" + comment;

        String result = commentService.getUsernameComments(username);

        assertEquals(expected, result);
        verify(commentRepository, times(1)).getByUsername(eq(comment.getUsername()));
    }

    @Test
    public void commentRepositoryShouldReturnWarningCauseNoCommentFound() {
        when(commentRepository.getByUsername(anyString())).thenReturn(Collections.emptyList());
        String expected = "Не найдено комментариев юзера";

        String result = commentService.getUsernameComments("usern");

        assertEquals(expected, result);
        verify(commentRepository, times(1)).getByUsername(eq("usern"));
    }

    @Test
    public void commentRepositoryShouldDeleteCommentByUsername() {
        String username = "usern";
        String commentText = "so good";
        Comment comment = new Comment(username, commentText);
        when(commentRepository.getByUsername(eq(username))).thenReturn(Collections.singletonList(comment));
        String expected = "Комментарии успешно удалены";

        String result = commentService.deleteUsernameComments(username);

        assertEquals(expected, result);
        verify(commentRepository, times(1)).getByUsername(eq(username));
        verify(commentRepository, times(1)).deleteByUsername(eq(username));
    }
}
