package at.watchlist.db.entities;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieId implements Serializable {
    @ManyToOne
    private Account account;
    @ManyToOne
    private MovieInfos movieInfos;

    public MovieId() {
    }

    public MovieId(Account account, MovieInfos movieInfos) {
        this.account = account;
        this.movieInfos = movieInfos;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public MovieInfos getMovieInfos() {
        return movieInfos;
    }

    public void setMovieInfos(MovieInfos movieInfos) {
        this.movieInfos = movieInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieId movieId = (MovieId) o;
        return account.equals(movieId.account) && movieInfos.equals(movieId.movieInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, movieInfos);
    }
}
