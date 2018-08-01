package otus.springfreamwork.jpa.domain.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import otus.springfreamwork.jpa.domain.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Transactional
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void genreRepositoryShouldInsertEntity() {
        Genre genre = new Genre("fantasy");
        genreRepository.insert(genre);

        TypedQuery<Genre> query = entityManager
                .createQuery(
                        "SELECT g FROM Genre g WHERE g.name = :name"
                        , Genre.class
                );
        query.setParameter("name", genre.getName());
        Genre genreFromRepo = query.getSingleResult();

        assertEquals(genre, genreFromRepo);
    }

    @Test
    public void genreRepositoryShouldGetGenreById() {
        Genre genre = new Genre("fantasy");

        entityManager.persist(genre);

        Genre genreFromRepository = genreRepository.getById(genre.getId());

        assertEquals(genre, genreFromRepository);
    }

    @Test
    public void genreRepositoryShouldGetGenreByName() {
        Genre genre = new Genre("fantasy");

        entityManager.persist(genre);

        Genre genreFromRepository = genreRepository.getByName(genre.getName());

        assertEquals(genre, genreFromRepository);
    }

    @Test
    public void genreRepositoryShouldGetAllgenres() {
        Genre genre = new Genre("fantasy");
        Genre genre_2 = new Genre("novel");

        entityManager.persist(genre);
        entityManager.persist(genre_2);

        List<Genre> genres = genreRepository.getAll();

        assertTrue(genres.contains(genre));
        assertTrue(genres.contains(genre_2));
    }

    @Test
    public void genreRepositoryShouldDeleteGenreById() {
        Genre genre = new Genre("fantasy");

        entityManager.persist(genre);
        int id = genre.getId();

        genreRepository.deleteById(id);

        Genre genreFromRepo = genreRepository.getById(id);
        assertNull(genreFromRepo);
    }

    @Test
    public void genreRepositoryShouldDeleteGenreByName() {
        Genre genre = new Genre("fantasy");

        entityManager.persist(genre);
        entityManager.detach(genre);

        genreRepository.deleteByName(genre.getName());

        Genre genreFromRepository = genreRepository.getByName(genre.getName());
        assertNull(genreFromRepository);
    }

    @Test
    public void genreRepositoryShouldReturnCount_2() {
        Genre genre = new Genre("fantasy");
        Genre genre_2 = new Genre("novel");

        entityManager.persist(genre);
        entityManager.persist(genre_2);
        entityManager.detach(genre);
        entityManager.detach(genre_2);

        long count = genreRepository.count();

        assertEquals(2, count);
    }
}