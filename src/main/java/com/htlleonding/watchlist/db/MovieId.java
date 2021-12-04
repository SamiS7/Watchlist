package com.htlleonding.watchlist.db;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MovieId {
    private Integer accountId;
    private String movieId;

    public MovieId() {
    }
    public MovieId(int accountId, String movieId) {
        this.accountId = accountId;
        this.movieId = movieId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
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
