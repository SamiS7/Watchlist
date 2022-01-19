package watchlist.models;

import java.time.LocalDateTime;

public class Reminder {
    private Long id;
    private Account account;
    private MovieInfos movieInfos;
    private LocalDateTime timeToWatch;

    public Reminder() {
    }

    public Reminder(Account account, MovieInfos movieInfos, LocalDateTime timeToWatch) {
        this.id = id;
        this.account = account;
        this.movieInfos = movieInfos;
        this.timeToWatch = timeToWatch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getTimeToWatch() {
        return timeToWatch;
    }

    public void setTimeToWatch(LocalDateTime timeToWatch) {
        this.timeToWatch = timeToWatch;
    }
}
