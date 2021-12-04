package com.htlleonding.watchlist.UI.pages;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Home extends Scene {

    public Home() {
        super(initRoot());

    }

    public static Parent initRoot() {
        HBox root = new HBox();
        VBox menu = new VBox();
        VBox content = new VBox();
        root.getChildren().addAll(menu, content);



        return root;
    }
}
