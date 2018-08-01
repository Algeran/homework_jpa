package otus.springfreamwork.jpa.domain.model;

import javax.persistence.Access;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.AccessType.PROPERTY;

@Entity
@Table(name = "GENRES")
@Access(PROPERTY)
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "genre_id")),
        @AttributeOverride(name = "name", column = @Column(name = "genre_name", nullable =  false, length = 100))
})
public class Genre extends SuperEntity {

    public Genre() {}

    public Genre(String name) {
        setName(name);
    }

    public Genre(int id, String name) {
        this(name);
        setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return Objects.equals(getName(), genre.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + getId() +
                ", name=" + getName() +
                '}';
    }
}
