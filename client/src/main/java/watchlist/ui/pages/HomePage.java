package watchlist.ui.pages;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import watchlist.enums.Category;
import watchlist.forServer.models.MovieInfos;
import watchlist.ui.components.MovieRow;

import java.util.List;

public class HomePage extends HBox {

    public HomePage() {
        ScrollPane scrollPane = new ScrollPane();

        VBox content = new VBox();
        content.getStyleClass().add("homePageContent");
        content.prefWidthProperty().bind(this.widthProperty());
        this.getStyleClass().add("content");
        content.getStyleClass().add("homePage");

        scrollPane.setContent(content);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.getChildren().add(scrollPane);
        scrollPane.getStyleClass().add("scrollPane");

        loadRow(Category.SHORTLY_SAVED, content);
        loadRow(Category.FAMOUS, content);
        loadRow(Category.SEEN, content);
        loadRow(Category.NOT_SEEN, content);
    }

    private void loadRow(Category category, VBox content) {
        List<MovieInfos> data = MovieRow.getMovieData(category);

        if (data != null && data.size() > 0) {
            content.getChildren().add(new MovieRow(data, category, this));
        }
    }

}
