package com.htlleonding.watchlist.ui.pages;

import com.htlleonding.watchlist.Main;
import com.htlleonding.watchlist.db.DAO;
import com.htlleonding.watchlist.db.dbclass.MovieInfos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MovieDetail extends BorderPane {
    private MovieInfos movieInfos;
    private boolean isSaved = false;

    public MovieDetail(MovieInfos movieInfos) {
        this.movieInfos = movieInfos;
        initContent();
    }

    public void initContent() {
        //isSaved = saved();
        this.getStyleClass().add("content");
        VBox vb1 = new VBox();
        vb1.getStyleClass().add("leftVB");
        VBox vb2 = new VBox();
        this.getStyleClass().add("borderpane");

        Label title = new Label(movieInfos.getTitle());
        title.getStyleClass().add("title");
        Label plot = new Label(movieInfos.getPlot());
        plot.getStyleClass().add("plot");
        plot.getStyleClass().add("plot");
        Label cast = new Label("Schauspieler: " + movieInfos.getCasting());
        Label genre = new Label(movieInfos.getGenres());
        Label rating = new Label(movieInfos.getRating() + "");
        Label year = new Label("Jahr: " + movieInfos.getYear());
        String bStr = !isSaved ? "Hinzufügen": "Löschen";
        Button saveForLater = new Button(bStr);
        saveForLater.getStyleClass().add("saveButton");
        saveForLater.setOnAction(actionEvent -> {
            //saveOrDelete();
        });

        vb1.getChildren().addAll(plot, cast, genre, rating, year);


        this.setTop(title);

        this.setLeft(vb1);

        this.setBottom(saveForLater);

        Image image = new Image(movieInfos.getPosterName());
        ImageView iv = new ImageView(image);
        iv.prefHeight(1500);
        this.setRight(iv);

        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        BorderPane.setAlignment(vb1, Pos.CENTER_LEFT);
        BorderPane.setAlignment(saveForLater, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(iv, Pos.TOP_CENTER);
    }

    private void saveOrDelete() {
        if (isSaved) {
            DAO.getInstance().persist(movieInfos);
        }else {
            DAO.getInstance().delete(movieInfos);
        }
    }

    private boolean saved() {
        return DAO.getInstance().isSaved(movieInfos.getId(), Main.getUserId());
    }

    public MovieInfos getMovieInfos() {
        return movieInfos;
    }

    public void setMovieInfos(MovieInfos movieInfos) {
        this.movieInfos = movieInfos;
    }
}
