package at.watchlist.db.dbclass;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieId implements Serializable {
    private Integer accountId;
    private String movieId;

    public MovieId() {
    }

    public MovieId(Integer accountId, String movieId) {
        this.accountId = accountId;
        this.movieId = movieId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieId movieId1 = (MovieId) o;
        return accountId.equals(movieId1.accountId) && movieId.equals(movieId1.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, movieId);
    }
}
