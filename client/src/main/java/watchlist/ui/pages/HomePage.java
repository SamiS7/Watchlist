package watchlist.ui.pages;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import watchlist.Main;
import watchlist.enums.ListCategory;
import watchlist.ui.components.MovieRow;

public class HomePage extends HBox {
    private static int rowLimit = 10;

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

        content.getChildren().addAll(new MovieRow(ListCategory.SHORTLY_SAVED, rowLimit, this),
                new MovieRow(ListCategory.SEEN, rowLimit, this),
                new MovieRow(ListCategory.NOT_SEEN, rowLimit, this),
                new MovieRow(ListCategory.FAMOUS, rowLimit, this));
    }

}
