package com.htlleonding.watchlist;

import com.htlleonding.watchlist.ui.pages.HomePage;
import com.htlleonding.watchlist.ui.pages.SearchPage;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static Integer userId = 0;
    private Integer width = 900;
    private Integer height = 600;
    private Node currentPage;

    @Override
    public void start(Stage stage) {
        HBox root = new HBox();

        VBox menu = new VBox();
        menu.getStyleClass().add("menu");
        Image menuIcon = new Image(Main.class.getResourceAsStream("/images/icons/menuIconw.png"));
        ImageView menuIV = new ImageView(menuIcon);
        menuIV.setFitWidth(40);
        menuIV.setFitHeight(40);
        menuIV.getStyleClass().add("menuIV");
        Button menuIconB = new Button();
        menuIconB.setGraphic(menuIV);
        menuIconB.getStyleClass().add("menuIcon");
        Button homeB = new Button("Startseite");
        //Button myWatchlistB = new Button("Meine Merkliste");
        //Button seenB = new Button("Gesehen");
        //Button notSeenB = new Button("Nicht gesehen");
        //Button famousB = new Button("Beliebt");
        Button searchB = new Button("Suchen");

        //VBox v1 = new VBox(homeB, myWatchlistB, seenB, notSeenB, famousB, searchB);
        VBox v1 = new VBox(homeB, searchB);
        menu.getChildren().addAll(menuIconB, v1);

        menuIconB.setOnAction(actionEvent -> {
            double w = menu.getPrefWidth();
            if (w > 120) {
                menu.setMinWidth(120);
                menu.setPrefWidth(120);
            } else {
                menu.setPrefWidth(400);
            }
        });

        HomePage homePage = new HomePage();
        currentPage = new SearchPage();

        homeB.setOnAction(actionEvent -> {
            currentPage = new HomePage();
            root.getChildren().setAll(menu, currentPage);
        });
        searchB.setOnAction(actionEvent -> {
            currentPage = new SearchPage();
            root.getChildren().setAll(menu, currentPage);
        });

        root.getStylesheets().add(getClass().getResource("/css/index.css").toExternalForm());
        root.getChildren().addAll(menu, currentPage);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Watchlist");
        stage.show();
    }

    public static Integer getUserId() {
        return userId;
    }

    public static void setUserId(Integer userId) {
        Main.userId = userId;
    }

    public static void main(String[] args) {
        launch(args);
    }
}