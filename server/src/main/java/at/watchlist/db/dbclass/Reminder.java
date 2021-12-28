package at.watchlist.db.dbclass;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EmbeddedId
    private MovieId movieId;
    private LocalDateTime timeToWatch;

    public Reminder() {
    }

    public Reminder(Long id, MovieId movieId, LocalDateTime timeToWatch) {
        this.id = id;
        this.movieId = movieId;
        this.timeToWatch = timeToWatch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovieId getMovieId() {
        return movieId;
    }

    public void setMovieId(MovieId movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getTimeToWatch() {
        return timeToWatch;
    }

    public void setTimeToWatch(LocalDateTime timeToWatch) {
        this.timeToWatch = timeToWatch;
    }
}
