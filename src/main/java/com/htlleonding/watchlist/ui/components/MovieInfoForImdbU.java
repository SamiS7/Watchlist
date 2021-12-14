package com.htlleonding.watchlist.ui.components;

public class MovieInfoForImdbU {
    private String id, title, year, plot, poster;
    private MovieInfoForImdbO.Trailer trailer;
    private double rating;

    public MovieInfoForImdbU(String id, String title, String year, String plot, String poster, MovieInfoForImdbO.Trailer trailer, double rating) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.plot = plot;
        this.poster = poster;
        this.trailer = trailer;
        this.rating = rating;
    }

    public class Trailer {
        private String link;

        public Trailer(String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public MovieInfoForImdbO.Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(MovieInfoForImdbO.Trailer trailer) {
        this.trailer = trailer;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
