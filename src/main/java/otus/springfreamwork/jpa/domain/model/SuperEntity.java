package otus.springfreamwork.jpa.domain.model;

import javax.persistence.Access;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;

import static javax.persistence.AccessType.PROPERTY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.JOINED;

@MappedSuperclass
@Inheritance(strategy = JOINED)
@Access(PROPERTY)
abstract class SuperEntity {

    private int id;
    private String name;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
