package com.htlleonding.watchlist.db;

import javafx.scene.image.Image;

public class Poster {
    private Image image;
    private String id;
    private String titel;

    public Poster(Image image, String id, String titel) {
        this.image = image;
        this.id = id;
        this.titel = titel;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
