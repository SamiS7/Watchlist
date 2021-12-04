package com.htlleonding.watchlist;

import com.htlleonding.watchlist.UI.pages.Home;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    Integer userId;
    Integer width;
    Integer height;
    @Override
    public void start(Stage stage) {
        Home homePage = new Home(userId, width, height);
        stage.setScene(homePage);
        stage.setTitle("Watchlist");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}