package otus.springfreamwork.jpa.com.repositories.listeners;

import otus.springfreamwork.jpa.domain.model.Book;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.YEAR;

public class BookAgeCalculationListener {

    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculate(Book book) {
        if (book.getPublishedDate() == null) {
            book.setAgeYears(-1);
            return;
        }

        Date publishedDate = book.getPublishedDate();
        Calendar published = Calendar.getInstance();
        published.setTime(publishedDate);
        Calendar now = Calendar.getInstance();
        int adjust = 0;
        if (now.get(DAY_OF_YEAR) - published.get(DAY_OF_YEAR) < 0) {
            adjust = -1;
        }
        book.setAgeYears(now.get(YEAR) - published.get(YEAR) + adjust);
    }

}
