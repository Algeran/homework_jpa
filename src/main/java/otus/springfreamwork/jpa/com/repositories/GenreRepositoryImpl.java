package otus.springfreamwork.jpa.com.repositories;

import org.springframework.stereotype.Repository;
import otus.springfreamwork.jpa.domain.dao.GenreRepository;
import otus.springfreamwork.jpa.domain.model.Genre;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GenreRepositoryImpl extends AbstractDataRepository<Genre> implements GenreRepository {

    public GenreRepositoryImpl() {
        super(Genre.class);
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = getEntityManager().createQuery(
                "SELECT g FROM Genre g WHERE g.name = :name"
                , Genre.class);
        query.setParameter("name", name);
        List<Genre> genres = query.getResultList();
        return genres.isEmpty() ? null : genres.get(0);
    }

    @Override
    public void deleteById(int id) {
        Genre genre = getById(id);
        delete(genre);
    }

    @Override
    public void deleteByName(String name) {
        Query query = getEntityManager().createQuery(
                "DELETE FROM Genre g WHERE g.name = :name"
        );
        query.setParameter("name", name);
        query.executeUpdate();
    }
}
