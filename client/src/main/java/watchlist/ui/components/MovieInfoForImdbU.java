package watchlist.ui.components;

public class MovieInfoForImdbU {
    private String id, title, year, plot, poster;
    private Trailer trailer;
    private double rating;

    public MovieInfoForImdbU(String id, String title, String year, String plot, String poster, Trailer trailer, double rating) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.plot = plot;
        this.poster = poster;
        this.trailer = trailer;
        this.rating = rating;
    }

    public class Trailer {
        private String id;

        public Trailer(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
