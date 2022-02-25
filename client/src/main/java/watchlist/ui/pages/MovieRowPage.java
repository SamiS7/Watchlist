package watchlist.ui.pages;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import watchlist.Main;
import watchlist.models.MovieInfos;
import watchlist.ui.components.MovieRow;
import watchlist.ui.components.enums.Category;

import java.util.LinkedList;
import java.util.List;

public class MovieRowPage extends VBox implements Reloadable {
    private Category category;
    private List<MovieInfos> movieInfosList = new LinkedList<>();
    private Node fromPage;

    public MovieRowPage(Category category, Node fromPage) {
        this.category = category;
        this.fromPage = fromPage;

        addAndInit();
    }

    public MovieRowPage(Category category, List<MovieInfos> movieInfosList, Node fromPage) {
        this.category = category;
        this.movieInfosList = movieInfosList;
        this.fromPage = fromPage;

        addAndInit();
    }

    @Override
    public void initBody() {
        this.getChildren().clear();

        Label title = new Label(category.getTitel());

        TilePane tilePane = new TilePane();
        tilePane.getStyleClass().add("mRTilePane");
        tilePane.setTileAlignment(Pos.CENTER);
        tilePane.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(tilePane);
        tilePane.prefWidthProperty().bind(scrollPane.widthProperty());
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.getStyleClass().addAll("scrollPane", "content");

        this.getChildren().addAll(title, scrollPane);
        this.getStyleClass().addAll("movieRowPage");

        initMovieRowContent(fromPage, tilePane);
    }

    public void initMovieRowContent(Node fromPage, Pane pane) {
        this.movieInfosList = getMovieData();
        new Thread(() -> {
            if (this.movieInfosList.size() > 0) {
                for (MovieInfos m : this.movieInfosList) {
                    Image image = new Image(m.getPosterUrl());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100*2.36);
                    imageView.setFitHeight(100*3.21);

                    Button button = new Button();
                    button.setGraphic(imageView);
                    button.setBackground(null);
                    button.setStyle("-fx-padding: 0");

                    button.setOnAction(actionEvent -> {
                        MovieDetail.showMovieDetail(m, this);
                    });
                    Platform.runLater(() -> pane.getChildren().add(button));
                }

            } else {
                Label label = new Label("Nichts zu dieser Kategorie gefunden!");
                label.getStyleClass().add("nothingFound");

                Platform.runLater(() -> pane.getChildren().add(label));
            }
        }).start();
    }

    public void show() {
        Node root = Main.getRoot();
        ((HBox) root).getChildren().remove(fromPage);
        ((HBox) root).getChildren().add(this);
    }

    private List<MovieInfos> getMovieData() {
        return MovieRow.getMovieData(this.category, 0, -1);
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
