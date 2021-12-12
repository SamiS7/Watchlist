package com.htlleonding.watchlist;

import com.htlleonding.watchlist.ui.pages.Home;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    Integer userId = 0;
    Integer width = 900;
    Integer height = 600;
    @Override
    public void start(Stage stage) {
        Home.setUserId(userId);
        HBox root = new HBox();
        Home homePage = new Home();

        VBox menu = new VBox();
        menu.getStyleClass().add("menu");
        //Image menuIcon = new Image(Home.class.getResourceAsStream("/images/menuIcon.png"));
        //ImageView menuIV = new ImageView(menuIcon);
        Button menuIconB = new Button();
        //menuIconB.setGraphic(menuIV);
        menuIconB.getStyleClass().add("menuIcon");
        Button myWatchlistB = new Button("Meine Merkliste");
        Button seenB = new Button("Gesehen");
        Button notSeenB = new Button("Nicht gesehen");
        Button famousB = new Button("Beliebt");
        Button searchB = new Button("Suchen");

        menu.getChildren().addAll(menuIconB, myWatchlistB, seenB, notSeenB, famousB, searchB);

        root.getStylesheets().add(getClass().getResource("/css/menu.css").toExternalForm());
        root.getChildren().addAll(menu, homePage);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Watchlist");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}