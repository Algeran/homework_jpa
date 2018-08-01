package otus.springfreamwork.jpa.com.repositories;

import org.springframework.stereotype.Repository;
import otus.springfreamwork.jpa.domain.dao.AuthorRepository;
import otus.springfreamwork.jpa.domain.model.Author;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AuthorRepositoryImpl extends AbstractDataRepository<Author> implements AuthorRepository {

    public AuthorRepositoryImpl() {
        super(Author.class);
    }

    @Override
    public Author getByNameAndSurname(String name, String surname) {
        TypedQuery<Author> query = getEntityManager().createQuery(
                "SELECT a FROM Author a WHERE a.name = :name AND a.surname = :surname"
                , Author.class);
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        List<Author> authors = query.getResultList();
        return authors.isEmpty() ? null : authors.get(0);
    }

    @Override
    public void deleteById(int id) {
        Author author = getById(id);
        delete(author);
    }

    @Override
    public void deleteByNameAndSurname(String name, String surname) {
        Query query = getEntityManager().createQuery(
                "DELETE FROM Author a WHERE a.name = :name AND a.surname = :surname");
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        query.executeUpdate();
    }
}
