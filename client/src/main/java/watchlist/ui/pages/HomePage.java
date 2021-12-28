package watchlist.ui.pages;

import javafx.scene.layout.VBox;
import watchlist.Main;
import watchlist.enums.ListCategory;
import watchlist.ui.components.MovieRow;

public class HomePage extends VBox {
    private static int rowLimit = 5;

    public HomePage() {
        VBox content = new VBox();
        this.getChildren().add(content);

        content.getStyleClass().add("content");
        content.getChildren().addAll(new MovieRow(ListCategory.SHORTLY_SAVED, Main.getUserId(), rowLimit));
    }

}
