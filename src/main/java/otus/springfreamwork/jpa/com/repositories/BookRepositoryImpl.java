package otus.springfreamwork.jpa.com.repositories;

import org.springframework.stereotype.Repository;
import otus.springfreamwork.jpa.domain.dao.BookRepository;
import otus.springfreamwork.jpa.domain.model.Book;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BookRepositoryImpl extends AbstractDataRepository<Book> implements BookRepository {

    public BookRepositoryImpl() {
        super(Book.class);
    }

    @Override
    public Book getByName(String name) {
        TypedQuery<Book> query = getEntityManager()
                .createQuery("SELECT b FROM Book b WHERE b.name = :name"
                        , Book.class
                );
        query.setParameter("name", name);
        List<Book> books = query.getResultList();
        return books.isEmpty() ? null : books.get(0);
    }

    @Override
    public void deleteById(int id) {
        Book book = getById(id);
        delete(book);
    }

    @Override
    public void deleteByName(String name) {
        Query query = getEntityManager().createQuery("DELETE FROM Book b WHERE b.name = :name");
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    public List<Book> getByAuthorId(int authorId) {
        TypedQuery<Book> namedQuery = getEntityManager().createNamedQuery(Book.GET_BY_AUTHOR_ID, Book.class);
        namedQuery.setParameter("id", authorId);
        return namedQuery.getResultList();
    }

    @Override
    public List<Book> getByGenreId(int genreId) {
        TypedQuery<Book> namedQuery = getEntityManager().createNamedQuery(Book.GET_BY_GENRE_ID, Book.class);
        namedQuery.setParameter("id", genreId);
        return namedQuery.getResultList();
    }
}
