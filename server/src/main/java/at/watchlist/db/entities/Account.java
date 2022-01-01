package at.watchlist.db.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "movieId.account", cascade = CascadeType.ALL)
    private List<SavedMovie> movies = new ArrayList<>();
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Reminder> reminders = new ArrayList<>();
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<SearchHistory> searchHistories = new ArrayList<>();

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addMovies(MovieInfos movieInfos) {
        MovieId movieId = new MovieId(this, movieInfos);
        SavedMovie savedMovie = new SavedMovie(movieId, LocalDateTime.now(), false, false);

        this.movies.add(savedMovie);
    }

    public boolean removeMovie(MovieInfos movieInfos) {
        return movies.remove(movieInfos);
    }

    public void addReminder(MovieInfos movieInfos, LocalDateTime time) {
        Reminder reminder = new Reminder(this, movieInfos, time);
        reminders.add(reminder);
    }

    public void addSearchHistory(String searchStr) {
        SearchHistory searchHistory = new SearchHistory(this, searchStr);
        searchHistories.add(searchHistory);
    }

    public List<SavedMovie> getMovies() {
        return movies;
    }

    public void setMovies(List<SavedMovie> movies) {
        this.movies = movies;
    }

    public List<Reminder> getReminder() {
        return reminders;
    }

    public void setReminder(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public List<SearchHistory> getSearchHistories() {
        return searchHistories;
    }

    public void setSearchHistories(List<SearchHistory> searchHistories) {
        this.searchHistories = searchHistories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
