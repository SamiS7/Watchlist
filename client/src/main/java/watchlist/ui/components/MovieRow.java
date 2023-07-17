package watchlist.ui.components;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import watchlist.ui.components.enums.Category;
import watchlist.models.MovieInfos;
import watchlist.request.Selection;
import watchlist.ui.pages.MovieDetail;
import watchlist.ui.pages.MovieRowPage;

import java.util.List;

public class MovieRow extends VBox {
    private List<MovieInfos> movieInfos;
    private Category category;
    private Node fromPage;

    public MovieRow(List<MovieInfos> movieInfos, Category category, Node fromPage) {
        this.movieInfos = movieInfos;
        this.fromPage = fromPage;
        this.category = category;
        initBox();
    }

    private void initBox() {
        Label title = new Label(category.getTitel());
        ScrollPane scrollPane = getContent();
        scrollPane.setBackground(null);

        this.getChildren().addAll(title, scrollPane);
        this.getStyleClass().add("movieRow");
        this.setSpacing(5);
    }

    private ScrollPane getContent() {
        HBox h = new HBox();
        h.getStyleClass().add("imageBox");
        ScrollPane scrollPane = new ScrollPane(h);
        scrollPane.getStyleClass().add("scrollPane");

        initMovieRowContent(movieInfos, fromPage, h);

        //h.prefWidthProperty().bind(scrollPane.widthProperty());
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefSize(500, 325);

        return scrollPane;
    }

    public static List<MovieInfos> getMovieData(Category listCategory, int start, int end) {
        try {
            return switch (listCategory) {
                case SHORTLY_SAVED -> Selection.getINSTANCE().getShortlyAdded(start, end);
                case SEEN -> Selection.getINSTANCE().getWatchedMovies(start, end);
                case NOT_SEEN -> Selection.getINSTANCE().getNotWatchedMovies(start, end);
                case FAMOUS -> Selection.getINSTANCE().getBestRated(start, end);
            };
        } catch (UnirestException e) {
        }
        return null;
    }

    public static List<MovieInfos> getMovieData(Category listCategory) {
        return getMovieData(listCategory, 0, 9);
    }

    public void initMovieRowContent(List<MovieInfos> movieInfosList, Node fromPage, Pane pane) {
        new Thread(() -> {
            if (movieInfosList.size() > 0) {
                for (MovieInfos m : movieInfosList) {
                    Image image = new Image(m.getPosterUrl(), 0, 300, true, false);

                    ImageView imageView = new ImageView(image);
                    //imageView.setPreserveRatio(true);
                    //imageView.setFitHeight(300);

                    Button button = new Button();
                    button.setGraphic(imageView);
                    button.setBackground(null);
                    button.setStyle("-fx-padding: 0");

                    button.setOnAction(actionEvent -> {
                        MovieDetail.showMovieDetail(m, fromPage);
                    });
                    Platform.runLater(() -> pane.getChildren().add(button));
                }

                if (movieInfosList.size() >= 10) {
                    Button button = new Button("Mehr");
                    button.getStyleClass().add("movieRowMoreB");
                    button.setOnAction(actionEvent -> {
                        new MovieRowPage(this.category, this.fromPage).show();
                    });

                    Platform.runLater(() -> pane.getChildren().add(button));
                }

            } else {
                Label label = new Label("Nichts zu dieser Kategorie gefunden!");
                Platform.runLater(() -> pane.getChildren().add(label));
            }
        }).start();
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
}
