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
import watchlist.ui.components.enums.Category;
import watchlist.models.MovieInfos;
import watchlist.request.Selection;
import watchlist.ui.pages.MovieDetail;

import java.util.List;

public class MovieRow extends VBox {
    private List<MovieInfos> movieInfos;
    private Category category;
    private Node fromPage;


    //region constructor, getter & setter
    public MovieRow(List<MovieInfos> movieInfos, Category category, Node fromPage) {
        this.movieInfos = movieInfos;
        this.fromPage = fromPage;
        this.category = category;
        initBox();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Node getFromPage() {
        return fromPage;
    }

    public void setFromPage(Node fromPage) {
        this.fromPage = fromPage;
    }

    //endregion

    private void initBox() {
        Label titel = new Label(category.getTitel());
        ScrollPane scrollPane = getContent();
        scrollPane.setBackground(null);

        this.getChildren().addAll(titel, scrollPane);
        this.getStyleClass().add("movieRow");
        this.setSpacing(5);
    }

    private ScrollPane getContent() {
        HBox h = new HBox();
        h.getStyleClass().add("imageBox");
        ScrollPane scrollPane = new ScrollPane(h);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                if (movieInfos.size() > 0) {
                    for (MovieInfos m : movieInfos) {
                        Image image = new Image(m.getPosterUrl());
                        ImageView imageView = new ImageView(image);
                        imageView.setPreserveRatio(true);
                        imageView.setFitHeight(300);

                        Button button = new Button();
                        button.setGraphic(imageView);
                        button.setBackground(null);
                        button.setOnAction(actionEvent -> {
                            MovieDetail.showMovieDetail(m, fromPage);
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

        h.prefWidthProperty().bind(scrollPane.widthProperty());
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefSize(500, 325);

        return scrollPane;
    }

    public static List<MovieInfos> getMovieData(Category listCategory) {
        try {
            return switch (listCategory) {
                case SHORTLY_SAVED -> Selection.getINSTANCE().getShortlyAdded(0, 9);
                case SEEN -> Selection.getINSTANCE().getWatchedMovies(0, 9);
                case NOT_SEEN -> Selection.getINSTANCE().getNotWatchedMovies(0, 9);
                case FAMOUS -> Selection.getINSTANCE().getBestRated(0, 9);
            };
        } catch (UnirestException e) {
        }
        return null;
    }
}
