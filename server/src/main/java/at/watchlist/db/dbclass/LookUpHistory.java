package at.watchlist.db.dbclass;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LookUpHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EmbeddedId
    private MovieId movieId;
    private LocalDateTime time;

    public LookUpHistory() {
    }

    public LookUpHistory(Long id, MovieId movieId, LocalDateTime time) {
        this.id = id;
        this.movieId = movieId;
        this.time = time;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
