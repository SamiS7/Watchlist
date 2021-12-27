package at.watchlist.db.dbclass;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class SavedMovie {
    @EmbeddedId
    private MovieId movieId;
    private LocalDateTime time;
    private Boolean seen;
    private Boolean liked;

    public SavedMovie() {
    }

    public SavedMovie(MovieId movieId, LocalDateTime time, Boolean seen, Boolean liked) {
        this.movieId = movieId;
        this.time = time;
        this.seen = seen;
        this.liked = liked;
    }

    public SavedMovie(MovieId movieId, LocalDateTime time) {
        this.movieId = movieId;
        this.time = time;
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

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
