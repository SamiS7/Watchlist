package watchlist.forServer.serverClass;

public class MovieInfos {
    String id;
    String title;
    String year;
    String plot;
    String type;
    String genres;
    String stars;
    String posterUrl;
    String trailerUrl;
    String thumbNail;
    Double imdbRating;

    public MovieInfos() {
    }

    public MovieInfos(String id, String title, String year, String plot, String type, String genres, String stars, String posterUrl, String trailerUrl, String thumbNail, Double imdbRating) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.plot = plot;
        this.type = type;
        this.genres = genres;
        this.stars = stars;
        this.posterUrl = posterUrl;
        this.trailerUrl = trailerUrl;
        this.thumbNail = thumbNail;
        this.imdbRating = imdbRating;
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

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public Double getImdbRatin() {
        return imdbRating;
    }

    public void setImdbRatin(Double imdbRating) {
        this.imdbRating = imdbRating;
    }
}
