package com.htlleonding.watchlist.ui.pages;

import com.htlleonding.watchlist.ui.components.MovieRow;
import com.htlleonding.watchlist.enums.ListCategory;
import javafx.scene.layout.VBox;

public class Home extends VBox {
    private static Integer userId;
    private static int rowLimit = 5;

    public Home() {
        VBox content = new VBox();
        this.getChildren().add(content);

        content.getStyleClass().add("content");
        content.getChildren().addAll(new MovieRow(ListCategory.SHORTLY_SAVED, userId, rowLimit));
    }

    public static Integer getUserId() {
        return userId;
    }

    public static void setUserId(Integer userId) {
        Home.userId = userId;
    }
}
