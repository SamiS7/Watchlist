package at.watchlist.forServer.serverClass;

public class Poster {
    private String imageUrl, id, titel;

    public Poster(String imageUrl, String id, String titel) {
        this.imageUrl = imageUrl;
        this.id = id;
        this.titel = titel;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
}
