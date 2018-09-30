package otus.springfreamwork.jpa.com.repositories;

import org.springframework.stereotype.Repository;
import otus.springfreamwork.jpa.domain.dao.CommentRepository;
import otus.springfreamwork.jpa.domain.model.Comment;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends AbstractDataRepository<Comment> implements CommentRepository {

    public CommentRepositoryImpl() {
        super(Comment.class);
    }

    @Override
    public List<Comment> getByUsername(String username) {
        TypedQuery<Comment> query = getEntityManager()
                .createQuery("SELECT c FROM Comment c WHERE c.username = :username", Comment.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public void deleteById(int id) {
        Comment comment = getById(id);
        delete(comment);
    }

    @Override
    public void deleteByUsername(String username) {
        Query query = getEntityManager().createQuery("DELETE FROM Comment c WHERE c.username = :username");
        query.setParameter("username", username);
        query.executeUpdate();
    }

    @Override
    public List<Comment> getByBookName(String bookName) {
        TypedQuery<Comment> query = getEntityManager().createQuery(
                "SELECT c FROM Comment c LEFT JOIN c.books b WHERE b.name = :name"
                , Comment.class
        );
        query.setParameter("name", bookName);
        return query.getResultList();
    }
}
