package at.watchlist.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToMany(mappedBy = "movieId.account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SavedMovie> movies = new HashSet<>();
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<SearchHistory> searchHistories = new HashSet<>();

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

    public boolean removeMovie(SavedMovie savedMovie) {
        return movies.remove(savedMovie);
    }

    public void addSearchHistory(String searchStr) {
        SearchHistory searchHistory = new SearchHistory(this, searchStr);
        searchHistories.add(searchHistory);
    }

    public Set<SavedMovie> getMovies() {
        return movies;
    }

    public void setMovies(Set<SavedMovie> movies) {
        this.movies = movies;
    }

    public Set<SearchHistory> getSearchHistories() {
        return searchHistories;
    }

    public void setSearchHistories(Set<SearchHistory> searchHistories) {
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
