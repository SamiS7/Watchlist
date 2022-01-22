package watchlist.ui.pages;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import watchlist.models.MovieInfos;
import watchlist.ui.components.MovieRow;
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

        MovieRow.initMovieRowContent(movieInfosList, fromPage, tilePane.getChildren(), tilePane);
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
