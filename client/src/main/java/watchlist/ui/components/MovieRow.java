package watchlist.ui.components;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import watchlist.enums.ListCategory;
import watchlist.forServer.models.MovieInfos;
import watchlist.forServer.serverConn.Selection;
import watchlist.ui.pages.MovieDetail;

import java.util.List;

public class MovieRow extends VBox {
    private ListCategory listCategory;
    private int limit;
    private Node fromPage;

    //region constructor, getter & setter
    public MovieRow(ListCategory listCategory, int limit, Node fromPage) {
        this.listCategory = listCategory;
        this.limit = limit;
        this.fromPage = fromPage;
        initBox();
    }

    public ListCategory getListCategory() {
        return listCategory;
    }

    public void setListCategory(ListCategory listCategory) {
        this.listCategory = listCategory;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    //endregion

    public void initBox() {
        Label titel = new Label(listCategory.getTitel());
        ScrollPane scrollPane = getContent();
        scrollPane.setBackground(null);

        this.getChildren().addAll(titel, scrollPane);
        this.getStyleClass().add("movieRow");
    }

    public ScrollPane getContent() {
        HBox h = new HBox();
        h.getStyleClass().add("imageBox");
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                List<MovieInfos> movieData = getMovieData();
                if (movieData.size() > 0) {
                    for (MovieInfos m : movieData) {
                        Image image = new Image(m.getPosterUrl());
                        ImageView imageView = new ImageView(image);
                        imageView.setPreserveRatio(true);
                        imageView.setFitHeight(300);

                        Button button = new Button();
                        button.setGraphic(imageView);
                        button.setBackground(null);
                        button.setOnAction(actionEvent -> {
                            MovieDetail.showMovieDetail(m.getId(), fromPage);
                        });
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                h.getChildren().add(button);
                            }
                        });
                    }

                } else {
                    Label label = new Label("Nichts zu dieser Kategorie gefunden!");
                    h.getChildren().add(label);
                }
                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        ScrollPane scrollPane = new ScrollPane(h);
        h.prefWidthProperty().bind(scrollPane.widthProperty());
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return scrollPane;
    }

    public List<MovieInfos> getMovieData() throws UnirestException {
        return switch (listCategory) {
            case SHORTLY_SAVED -> Selection.getINSTANCE().getShortlyAdded(0, limit);
            case MY_WATCHLIST -> Selection.getINSTANCE().getWatchlist();
            case SEEN -> Selection.getINSTANCE().getWatchedMovies(0, limit);
            case NOT_SEEN -> Selection.getINSTANCE().getNotWatchedMovies(0, limit);
            case FAMOUS -> Selection.getINSTANCE().getBestRated(0, limit);
        };
    }
}
