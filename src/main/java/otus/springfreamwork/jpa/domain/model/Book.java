package otus.springfreamwork.jpa.domain.model;

import otus.springfreamwork.jpa.com.repositories.listeners.BookAgeCalculationListener;

import javax.persistence.Access;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.AccessType.PROPERTY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.DATE;
import static otus.springfreamwork.jpa.domain.model.Book.GET_BY_AUTHOR_ID;
import static otus.springfreamwork.jpa.domain.model.Book.GET_BY_GENRE_ID;

@Entity
@Table(name = "BOOKS")
@Access(PROPERTY)
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "book_id")),
        @AttributeOverride(name = "name", column = @Column(name = "book_name", nullable = false, length = 200))
})
@NamedQueries({
        @NamedQuery(name = GET_BY_AUTHOR_ID, query = "SELECT b FROM Book b LEFT JOIN b.authors a WHERE a.id = :id"),
        @NamedQuery(name = GET_BY_GENRE_ID, query = "SELECT b FROM Book b WHERE b.genre.id = :id")
})
@EntityListeners({BookAgeCalculationListener.class})
public class Book extends SuperEntity {

    public static final String GET_BY_AUTHOR_ID = "GET_BY_AUTHOR_ID";
    public static final String GET_BY_GENRE_ID = "GET_BY_GENRE_ID";

    private Date publishedDate;
    private int ageYears;
    private Map<Integer,String> parts;
    private Set<Author> authors;
    private Genre genre;

    public Book() { }

    public Book(String name, Date publishedDate, Map<Integer, String> parts, Set<Author> authors, Genre genre) {
        setName(name);
        this.publishedDate = publishedDate;
        this.parts = parts;
        this.authors = authors;
        this.genre = genre;
    }

    @Temporal(DATE)
    @Column(name = "published_date")
    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Transient
    public int getAgeYears() {
        return ageYears;
    }

    public void setAgeYears(int ageYears) {
        this.ageYears = ageYears;
    }

    @ElementCollection(fetch = LAZY)
    @CollectionTable(name = "PARTS")
    @MapKeyColumn(name = "PART")
    @Column(name = "PART_NAME")
    public Map<Integer, String> getParts() {
        return parts;
    }

    public void setParts(Map<Integer, String> parts) {
        this.parts = parts;
    }

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "author_id")
    )
    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @ManyToOne(cascade = ALL, optional = false)
    @JoinColumn(name = "genre_id")
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getAgeYears() == book.getAgeYears() &&
                Objects.equals(getPublishedDate(), book.getPublishedDate()) &&
                Objects.equals(getParts(), book.getParts()) &&
                Objects.equals(getAuthors(), book.getAuthors()) &&
                Objects.equals(getGenre(), book.getGenre());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPublishedDate(), getAgeYears(), getParts(), getAuthors(), getGenre());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", publishedDate=" + publishedDate +
                ", ageYears=" + ageYears +
                ", parts=" + parts +
                ", author=" + authors +
                ", genre=" + genre +
                '}';
    }
}
