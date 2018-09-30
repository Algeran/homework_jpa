package otus.springfreamwork.jpa.domain.model;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Objects;
import java.util.Set;

import static javax.persistence.AccessType.PROPERTY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "comments")
@Access(PROPERTY)
public class Comment {

    private int id;
    private String username;
    private String comment;
    private Set<Book> books;

    public Comment() {}

    public Comment(int id, String username, String comment) {
        this.id = id;
        this.username = username;
        this.comment = comment;
    }

    public Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "username", length = 200)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "comment", length = 400)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @OneToMany(cascade = ALL)
    @JoinTable(
            name = "COMMENT_BOOK",
            joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    )
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment1 = (Comment) o;
        return Objects.equals(getUsername(), comment1.getUsername()) &&
                Objects.equals(getComment(), comment1.getComment()) &&
                Objects.equals(getBooks(), comment1.getBooks());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUsername(), getComment(), getBooks());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", books=" + books +
                '}';
    }
}
