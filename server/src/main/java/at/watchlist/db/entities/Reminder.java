package at.watchlist.db.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
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
