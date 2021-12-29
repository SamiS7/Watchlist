package watchlist.ui.pages;

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
import watchlist.Main;
import watchlist.forServer.serverClass.MovieInfos;
import watchlist.forServer.serverConn.Insertion;
import watchlist.forServer.serverConn.Selection;

public class MovieDetail extends StackPane {
    private MovieInfos movieInfos;
    private boolean isSaved = false;
    private double contentWidth;
    private BorderPane borderPane;

    public MovieDetail(MovieInfos movieInfos) {
        this.movieInfos = movieInfos;
        this.contentWidth = contentWidth;
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
        Label rating = new Label("IMDB Bewertung: " + movieInfos.getImdbRatin());
        Label year = new Label("Jahr: " + movieInfos.getYear());

        String bStr = !isSaved ? "Speichern" : "Löschen";
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
            showTrailer();
        });

        vb1.getChildren().addAll(plot, cast, genre, rating, year);
        vb1.prefWidthProperty().bind(this.widthProperty().multiply(0.4));

        borderPane.setTop(title);

        borderPane.setLeft(vb1);

        borderPane.setBottom(hb);

        Image image = new Image(movieInfos.getPosterUrl());
        ImageView iv = new ImageView(image);
        iv.setPreserveRatio(true);
        iv.fitWidthProperty().bind(vb1.heightProperty().multiply(0.65));
        borderPane.setRight(iv);

        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        BorderPane.setAlignment(vb1, Pos.CENTER_LEFT);
        BorderPane.setAlignment(saveForLater, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(iv, Pos.TOP_CENTER);
    }

    public void showTrailer() {
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
