package watchlist.ui.pages;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import watchlist.Main;
import watchlist.models.MovieInfos;
import watchlist.ui.components.enums.Category;

import java.util.LinkedList;
import java.util.List;

public class MovieRowPage extends VBox {
    private Category category;
    private List<MovieInfos> movieInfosList = new LinkedList<>();
    private Node fromPage;

    public MovieRowPage(Category category, List<MovieInfos> movieInfosList, Node fromPage) {
        this.category = category;
        this.movieInfosList = movieInfosList;
        this.fromPage = fromPage;

        initBody();
    }

    public void initBody() {
        Label titel = new Label(category.getTitel());

        TilePane tilePane = new TilePane();

        this.getChildren().addAll(titel, tilePane);
        this.getStyleClass().addAll("movieRowPage", "content");

        initMovieRowContent(movieInfosList, fromPage, tilePane);
    }

    public void initMovieRowContent(List<MovieInfos> movieInfosList, Node fromPage, Pane pane) {
        new Thread(() -> {
            if (movieInfosList.size() > 0) {
                for (MovieInfos m : movieInfosList) {
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
                    Platform.runLater(() -> pane.getChildren().add(button));
                }

            } else {
                Label label = new Label("Nichts zu dieser Kategorie gefunden!");
                pane.getChildren().add(label);
            }
        }).start();
    }

    public static void showMovieRowPage(Category category, List<MovieInfos> movieInfos, Node fromPage) {
        MovieRowPage movieRowPage = new MovieRowPage(category, movieInfos, fromPage);

        Node root = Main.getRoot();
        ((HBox) root).getChildren().remove(fromPage);
        ((HBox) root).getChildren().add(movieRowPage);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<MovieInfos> getMovieInfosList() {
        return movieInfosList;
    }

    public void setMovieInfosList(List<MovieInfos> movieInfosList) {
        this.movieInfosList = movieInfosList;
    }
}
