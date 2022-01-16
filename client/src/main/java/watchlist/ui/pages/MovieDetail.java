package watchlist.ui.pages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import watchlist.forServer.models.MovieId;
import watchlist.forServer.models.MovieInfos;
import watchlist.forServer.models.Watchlist;
import watchlist.request.IMDBRequest;
import watchlist.request.MovieRequests;
import watchlist.ui.components.AlertError;
import watchlist.ui.components.MovieInfoForImdbO;
import watchlist.ui.components.MovieInfoForImdbU;

public class MovieDetail extends StackPane {
    private MovieInfos movieInfos;
    private Watchlist savedMovie;
    private Long userId = Main.userIdProperty().get();
    private BooleanProperty isSaved = new SimpleBooleanProperty();
    private BooleanProperty seen = new SimpleBooleanProperty();
    private BorderPane borderPane;

    public MovieDetail(MovieInfos movieInfos) {
        this.movieInfos = movieInfos;
        initContent();
    }

    public void initContent() {
        initSavedMovie();

        Main.userIdProperty().addListener((observable, oldVal, newVal) -> {
            initSavedMovie();
        });

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
        Label genre = new Label("Genres: " +  movieInfos.getGenres());
        Label rating = new Label("IMDB Bewertung: " + movieInfos.getImdbRating());
        Label year = new Label("Jahr: " + movieInfos.getYear());

        String bStr = isSaved.get() ? "Löschen" : "Speichern";
        HBox hb = new HBox();
        Button saveForLater = new Button(bStr);
        Button trailerB = new Button("Trailer anschauen");
        CheckBox seenCheckBox = new CheckBox("Schon gesehen");
        seenCheckBox.selectedProperty().bindBidirectional(seen);
        hb.getChildren().addAll(saveForLater, seenCheckBox, trailerB);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(20);
        saveForLater.getStyleClass().add("saveButton");
        trailerB.getStyleClass().add("trailerButton");
        seenCheckBox.getStyleClass().add("seenCheckBox");
        seenCheckBox.setDisable(saveForLater.isDisable());

        saveForLater.disableProperty().bind(Main.userIdProperty().lessThan(0));
        isSaved.addListener((observable, oldVal, newVal) -> {
            saveForLater.setText(newVal ? "Löschen" : "Speichern");
        });

        seenCheckBox.disableProperty().bind(saveForLater.disableProperty().or(isSaved.not()));

        saveForLater.setOnAction(actionEvent -> {
            try {
                HttpResponse response;
                if (isSaved.get()) {
                    response = MovieRequests.removeMovie(movieInfos.getId(), userId);
                } else {
                    response = MovieRequests.saveMovie(movieInfos, userId);
                }

                System.out.println(response.getStatus() + ": " + response.getStatusText());
                //if (response.getStatus() == 200) {
                    initSavedMovie();
                //}
            } catch (UnirestException e) {
                e.printStackTrace();
                new AlertError("Technische Probleme!", "Es sind technische Probleme aufgetreten. Versuchen es später erneut!");
            }

        });

        seenCheckBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
            Watchlist watchlist = new Watchlist(new MovieId(Main.getAccount(), this.movieInfos), seen.get(), false);
            try {
                HttpResponse response = MovieRequests.updateMovie(watchlist);

                if (response.getStatus() == 200) {
                    initSavedMovie();
                }
            } catch (UnirestException e) {
                e.printStackTrace();
            }
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
        iv.fitHeightProperty().bind(vb1.heightProperty().multiply(0.8));
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

    public void initSavedMovie() {
        if (userId >= 0) {
            try {
                var response = MovieRequests.getSavedMovie(movieInfos.getId(), userId);

                if (response.getStatus() == 200) {
                    savedMovie = response.getBody();
                    updateProperties(true, savedMovie.getSeen());
                } else if (response.getStatus() == 404) {
                    updateProperties(false, false);
                }
            } catch (UnirestException e) {
                updateProperties(false, false);
            }
        }
    }

    public void updateProperties(boolean isSaved, boolean seen) {
        this.isSaved.set(isSaved);
        this.seen.set(seen);
    }

    public static void showMovieDetail(String pId, Node fromPage) {
        Task<JsonObject> jo = IMDBRequest.request("https://imdb-api.com/en/API/Title/k_46caativ/" + pId + "/trailer");

        //Task<JsonObject> jo = IMDBRequest.requestWithRapidApi("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/film/" + pId);


        jo.setOnSucceeded(action -> {


            MovieInfoForImdbO m = null;
            try {
                m = asMovieInfo(jo.get(), MovieInfoForImdbO.class);
            } catch (Exception e) {
                new AlertError("Server Probleme", "Die Details des gewünschten Films kann nicht aufgerufen werden!");
                e.printStackTrace();
            }

            /*
            MovieInfoForImdbU m = null;
            try {
                m = asMovieInfo(jo.get(), MovieInfoForImdbU.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
             */

            MovieDetail movieDetail = new MovieDetail(convertToMovieInfo(m));

            Node root = Main.getRoot();
            ((HBox) root).getChildren().remove(fromPage);
            ((HBox) root).getChildren().add(movieDetail);

        });
    }

    private static <T> T asMovieInfo(JsonObject jsonObject, Class<T> classOfT) {
        return new Gson().fromJson(jsonObject, classOfT);
    }

    private static MovieInfos convertToMovieInfo(Object o) {
        if (o instanceof MovieInfoForImdbO m) {
            return new MovieInfos(m.getId(), m.getTitle(), m.getYear(), m.getPlot(), m.getType(), m.getGenres(),
                    m.getStars(), m.getImage(), m.getTrailer().getLinkEmbed(), m.getImDbRatin());
        } else if (o instanceof MovieInfoForImdbU m) {
            return new MovieInfos(m.getId(), m.getTitle(), m.getYear(), m.getPlot(), null, null,
                    null, m.getPoster(), "https://www.imdb.com/video/imdb/" + m.getTrailer().getId() + "/imdb/embed", m.getRating());
        }
        return null;
    }
}
