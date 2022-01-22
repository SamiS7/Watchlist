package watchlist.models;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private Long id;
    private String username;
    private String password;
    private List<Watchlist> movies = new ArrayList<>();

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<Watchlist> getMovies() {
        return movies;
    }

    public void setMovies(List<Watchlist> movies) {
        this.movies = movies;
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
