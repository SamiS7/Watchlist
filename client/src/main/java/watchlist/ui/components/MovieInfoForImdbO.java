package watchlist.ui.components;

public class MovieInfoForImdbO {
    private String id, title, year, plot, type, genres, stars, image;
    private Trailer trailer;
    private double imDbRating;

    public MovieInfoForImdbO(String id, String title, String year, String plot, String type, String genres, String stars, String image, Trailer trailer, double imDbRating) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.plot = plot;
        this.type = type;
        this.genres = genres;
        this.stars = stars;
        this.image = image;
        this.trailer = trailer;
        this.imDbRating = imDbRating;
    }

    public class Trailer {
       private String linkEmbed;

        public Trailer(String linkEmbed) {
            this.linkEmbed = linkEmbed;
        }

        public String getLinkEmbed() {
            return linkEmbed;
        }

        public void setLinkEmbed(String linkEmbed) {
            this.linkEmbed = linkEmbed;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public double getImDbRatin() {
        return imDbRating;
    }

    public void setImDbRatin(double imDbRating) {
        this.imDbRating = imDbRating;
    }
}
