package watchlist.models;

public class Watchlist {
    private MovieId movieId;
    private Boolean seen;
    private Boolean liked;

    public Watchlist() {
    }

    public Watchlist(MovieId movieId, Boolean seen, Boolean liked) {
        this.movieId = movieId;
        this.seen = seen;
        this.liked = liked;
    }

    public MovieId getMovieId() {
        return movieId;
    }

    public void setMovieId(MovieId movieId) {
        this.movieId = movieId;
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
