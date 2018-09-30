package otus.springfreamwork.jpa.domain.model;

import javax.persistence.Access;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.AccessType.PROPERTY;

@Entity
@Table(
        name = "AUTHORS",
        uniqueConstraints =
                @UniqueConstraint(columnNames = {"author_name", "author_surname"})
)
@Access(PROPERTY)
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "author_id")),
        @AttributeOverride(name = "name", column = @Column(name = "author_name", nullable = false))
})
public class Author extends SuperEntity {

    private String surname;
    private Conutry conutry;

    public Author() {}

    public Author(String name, String surname, Conutry conutry) {
        setName(name);
        this.surname = surname;
        this.conutry = conutry;
    }

    public Author(int id, String name, String surname, Conutry conutry) {
        this(name, surname, conutry);
        setId(id);
    }

    @Column(name = "AUTHOR_SURNAME", length = 100, nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "AUTHOR_COUNTRY")
    @Enumerated(value = EnumType.STRING)
    public Conutry getConutry() {
        return conutry;
    }

    public void setConutry(Conutry conutry) {
        this.conutry = conutry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(getName(), author.getName()) &&
                Objects.equals(surname, author.surname) &&
                conutry == author.conutry;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), surname, conutry);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", surname='" + surname + '\'' +
                ", conutry=" + conutry +
                '}';
    }
}
