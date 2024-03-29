package watchlist.ui.pages;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import watchlist.ui.components.enums.Category;
import watchlist.models.MovieInfos;
import watchlist.ui.components.MovieRow;

import java.util.List;

public class HomePage extends HBox implements Reloadable {
    private ScrollPane scrollPane;
    private VBox content;

    public HomePage() {
        addAndInit();
    }

    @Override
    public void initBody() {
        this.getChildren().clear();

        scrollPane = new ScrollPane();
        content = new VBox();
        content.maxWidthProperty().bind(scrollPane.widthProperty().subtract(30));

        content.getStyleClass().add("homePageContent");
        content.prefWidthProperty().bind(this.widthProperty());
        this.getStyleClass().addAll("content", "homePage");

        scrollPane.setContent(content);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.getChildren().add(scrollPane);
        scrollPane.getStyleClass().add("scrollPane");

        content.getChildren().clear();
        loadRow(Category.SHORTLY_SAVED);
        loadRow(Category.FAMOUS);
        loadRow(Category.SEEN);
        loadRow(Category.NOT_SEEN);
    }

    private void loadRow(Category category) {
        List<MovieInfos> data = MovieRow.getMovieData(category);

        if (data != null && data.size() > 0) {
            content.getChildren().add(new MovieRow(data, category, this));
        }
    }

}
