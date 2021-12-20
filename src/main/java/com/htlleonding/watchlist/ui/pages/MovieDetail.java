package com.htlleonding.watchlist.ui.pages;

import com.htlleonding.watchlist.Main;
import com.htlleonding.watchlist.db.DAO;
import com.htlleonding.watchlist.db.dbclass.MovieInfos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;


public class MovieDetail extends BorderPane {
    private MovieInfos movieInfos;
    private boolean isSaved = false;
    private double contentWidth;
    private double contentHeight;

    public MovieDetail(MovieInfos movieInfos, double contentWidth, double contentHeight) {
        this.movieInfos = movieInfos;
        this.contentWidth = contentWidth;
        this.contentHeight = contentHeight;

        Media media = new Media(new File("/home/sami/Downloads/10.mp4").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        this.setCenter(mediaView);
        //initContent();
    }

    public void initContent() {
        //isSaved = saved();
        this.getStyleClass().add("content");
        VBox vb1 = new VBox();
        vb1.getStyleClass().add("leftVB");
        this.getStyleClass().add("borderpane");

        Label title = new Label(movieInfos.getTitle());
        title.getStyleClass().add("title");
        Label plot = new Label(movieInfos.getPlot());
        plot.getStyleClass().add("plot");
        plot.getStyleClass().add("plot");
        Label cast = new Label("Schauspieler: " + movieInfos.getStars());
        Label genre = new Label(movieInfos.getGenres());
        Label rating = new Label(movieInfos.getImdbRatin() + "");
        Label year = new Label("Jahr: " + movieInfos.getYear());

        String bStr = !isSaved ? "Hinzufügen": "Löschen";
        HBox hb = new HBox();
        Button saveForLater = new Button(bStr);
        Button trailerB = new Button("Trailer anschauen");
        CheckBox seenCheckBox = new CheckBox("Schon gesehen");
        hb.getChildren().addAll(saveForLater, seenCheckBox, trailerB);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(20);
        saveForLater.getStyleClass().add("saveButton");
        trailerB.getStyleClass().add("trailerButton");
        seenCheckBox.getStyleClass().add("seenCheckBox");

        saveForLater.setOnAction(actionEvent -> {
            //saveOrDelete();
        });

        trailerB.setOnAction(actionEvent -> {
            var children = this.getChildren();
            Media media = new Media(movieInfos.getTrailerUrl());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            MediaView mediaView = new MediaView(mediaPlayer);
            this.setCenter(mediaView);
        });

        vb1.getChildren().addAll(plot, cast, genre, rating, year);

        this.setTop(title);

        this.setLeft(vb1);

        this.setBottom(hb);

        Image image = new Image(movieInfos.getPosterUrl());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(this.contentHeight - 150);
        iv.fitWidthProperty().bind(iv.fitHeightProperty().multiply(0.735));
        iv.resize(300,300);
        iv.prefWidth(500);
        iv.prefHeight(600);
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
