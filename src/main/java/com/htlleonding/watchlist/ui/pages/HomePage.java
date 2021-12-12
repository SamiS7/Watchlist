package com.htlleonding.watchlist.ui.pages;

import com.htlleonding.watchlist.Main;
import com.htlleonding.watchlist.ui.components.MovieRow;
import com.htlleonding.watchlist.enums.ListCategory;
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
