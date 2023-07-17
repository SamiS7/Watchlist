package watchlist.models;

public class MovieId {
    private Account account;
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
}
