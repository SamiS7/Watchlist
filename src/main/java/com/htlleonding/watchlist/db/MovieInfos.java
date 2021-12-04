package com.htlleonding.watchlist.db;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class MovieInfos {
    @Id
    private String id;
    private String title;
    private LocalDate year;
    private String plot;
    private String type;
    private String genres;
    private String posterName;
    private double rating;

    public MovieInfos() {
    }

    public MovieInfos(String id, String title, LocalDate year, String plot, String type, String genres, String posterName, Double rating) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.plot = plot;
        this.type = type;
        this.genres = genres;
        this.posterName = posterName;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
