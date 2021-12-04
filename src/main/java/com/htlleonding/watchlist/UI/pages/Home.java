package com.htlleonding.watchlist.UI.pages;

import com.htlleonding.watchlist.UI.components.MovieRow;
import com.htlleonding.watchlist.enums.ListCategory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Home extends Scene {
    private static Integer userId;
    private static int rowLimit = 5;

    public Home(Integer userId) {
        super(initRoot());
        this.userId = userId;

    }
    public Home(Integer userId, int w, int h) {
        super(initRoot(), w, h);
        this.userId = userId;

    }

    public static Parent initRoot() {
        HBox root = new HBox();
        VBox menu = new VBox();
        VBox content = new VBox();
        root.getChildren().addAll(menu, content);

        content.getChildren().addAll(new MovieRow(ListCategory.SHORTLY_SAVED, userId, rowLimit));

        return root;
    }
}
