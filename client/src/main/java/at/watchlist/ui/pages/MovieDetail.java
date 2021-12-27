package at.watchlist.ui.pages;

import at.watchlist.Main;
import at.watchlist.forServer.serverClass.MovieInfos;
import at.watchlist.forServer.serverConn.Insertion;
import at.watchlist.forServer.serverConn.Selection;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class MovieDetail extends StackPane {
    private MovieInfos movieInfos;
    private boolean isSaved = false;
    private double contentWidth;
    private double contentHeight;
    private BorderPane borderPane;

    public MovieDetail(MovieInfos movieInfos, double contentWidth, double contentHeight) {
        this.movieInfos = movieInfos;
        this.contentWidth = contentWidth;
        this.contentHeight = contentHeight;
        initContent();
    }

    public void initContent() {
        //isSaved = saved();
        borderPane = new BorderPane();
        this.getStyleClass().add("content");
        this.getChildren().add(borderPane);
        borderPane.getStyleClass().add("content");
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

        String bStr = !isSaved ? "Hinzufügen" : "Löschen";
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
            VBox trailerVB = new VBox();
            trailerVB.setMaxSize(900, 600);
            WebView webView = new WebView();
            this.getStyleClass().add("content");
            webView.getEngine().load(movieInfos.getTrailerUrl());
            webView.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/webView.css").toString());

            Button clearButton = new Button("X");
            clearButton.getStyleClass().add("trailerB");
            Button biggerB = new Button("+");
            biggerB.getStyleClass().add("trailerB");
            Button smallerB = new Button("-");
            smallerB.getStyleClass().add("trailerB");

            HBox hBox = new HBox(10, smallerB, biggerB, clearButton);
            hBox.setPrefSize(900, 20);
            hBox.setAlignment(Pos.CENTER_RIGHT);

            trailerVB.getChildren().addAll(hBox, webView);

            HBox overlay = new HBox();
            overlay.setPrefSize(2000, 2000);
            overlay.setStyle("-fx-background-color: rgba(0,0,0,0.6)");
            overlay.setAlignment(Pos.CENTER);
            overlay.getChildren().add(trailerVB);
            this.getChildren().add(overlay);

            clearButton.setOnAction(action -> {
                this.getChildren().remove(overlay);
                webView.getEngine().load("");
            });

            biggerB.setOnAction(actionEvent1 -> {
                double w = trailerVB.getMaxWidth() + 300;
                double h = trailerVB.getMaxHeight() + 200;
                trailerVB.setMaxSize(w, h);
                webView.setPrefSize(w, h);
            });
            smallerB.setOnAction(actionEvent1 -> {
                double w = trailerVB.getMaxWidth() - 300;
                double h = trailerVB.getMaxHeight() - 200;
                trailerVB.setMaxSize(w, h);
                webView.setPrefSize(w, h);
            });

        });

        vb1.getChildren().addAll(plot, cast, genre, rating, year);

        borderPane.setTop(title);

        borderPane.setLeft(vb1);

        borderPane.setBottom(hb);

        Image image = new Image(movieInfos.getPosterUrl());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(this.contentHeight - 150);
        iv.fitWidthProperty().bind(iv.fitHeightProperty().multiply(0.735));
        iv.resize(300, 300);
        iv.prefWidth(500);
        iv.prefHeight(600);
        borderPane.setRight(iv);

        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        BorderPane.setAlignment(vb1, Pos.CENTER_LEFT);
        BorderPane.setAlignment(saveForLater, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(iv, Pos.TOP_CENTER);
    }

    private void saveOrDelete() {
        if (isSaved) {
            Insertion.getInsertion().persist(movieInfos);
        } else {
            Insertion.getInsertion().delete(movieInfos);
        }
    }

    private boolean isSaved() {
        return Selection.getSelection().isSaved(movieInfos.getId(), Main.getUserId());
    }

    public MovieInfos getMovieInfos() {
        return movieInfos;
    }

    public void setMovieInfos(MovieInfos movieInfos) {
        this.movieInfos = movieInfos;
    }
}
