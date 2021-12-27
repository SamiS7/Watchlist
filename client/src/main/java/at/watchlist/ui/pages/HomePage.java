package at.watchlist.ui.pages;

import at.watchlist.Main;
import at.watchlist.enums.ListCategory;
import at.watchlist.ui.components.MovieRow;
import javafx.scene.layout.VBox;

public class HomePage extends VBox {
    private static int rowLimit = 5;

    public HomePage() {
        VBox content = new VBox();
        this.getChildren().add(content);

        content.getStyleClass().add("content");
        content.getChildren().addAll(new MovieRow(ListCategory.SHORTLY_SAVED, Main.getUserId(), rowLimit));
    }

}
