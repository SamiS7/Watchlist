package watchlist;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import watchlist.ui.pages.HomePage;
import watchlist.ui.pages.SearchPage;

import java.util.List;

public class Main extends Application {
    private static Integer userId = 0;
    private Integer width = 1200;
    private Integer height = 700;
    private Node currentPage;

    private HBox root = new HBox();

    @Override
    public void start(Stage stage) {
        VBox menu = getMenu(stage);

        root.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        root.getChildren().addAll(menu, currentPage);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Watchlist");
        stage.show();
    }

    public VBox getMenu(Stage stage) {
        VBox menu = new VBox();
        menu.getStyleClass().add("menu");
        Image menuIcon = new Image(Main.class.getResourceAsStream("/icons/menuIconw.png"));
        ImageView menuIV = new ImageView(menuIcon);
        menuIV.setFitWidth(40);
        menuIV.setFitHeight(40);
        menuIV.getStyleClass().add("menuIV");
        Button menuIconB = new Button();
        menuIconB.setGraphic(menuIV);
        menuIconB.getStyleClass().add("menuIcon");
        Button homeB = new Button("Startseite");
        Button myWatchlistB = new Button("Meine Merkliste");
        Button seenB = new Button("Gesehen");
        Button notSeenB = new Button("Nicht gesehen");
        Button famousB = new Button("Beliebt");
        Button searchB = new Button("Suchen");

        VBox v1 = new VBox(homeB, searchB, myWatchlistB, seenB, notSeenB, famousB);

        ImageView profileIV = new ImageView(new Image(this.getClass().getResourceAsStream("/icons/profile.png")));
        profileIV.setPreserveRatio(true);
        profileIV.fitWidthProperty().bind(menu.widthProperty().multiply(0.7));

        Button profileB = new Button();
        profileB.getStyleClass().add("profileB");
        profileB.setGraphic(profileIV);

        VBox profileContent = getProfileContent(false);
        profileContent.setVisible(false);

        profileB.setOnAction(actionEvent -> {
            profileContent.setVisible(!profileContent.isVisible());
        });

        VBox v2 = new VBox(10, profileContent, profileB);
        v2.setPrefSize(400, 1500);
        v2.setStyle("-fx-padding: 0 0 20 0");
        v2.setAlignment(Pos.BOTTOM_CENTER);

        menu.getChildren().addAll(menuIconB, v1, v2);

        menuIconB.setOnAction(actionEvent -> {
            double w = menu.getPrefWidth();
            if (w > 120) {
                menu.setMinWidth(120);
                menu.setPrefWidth(120);
            } else {
                menu.setPrefWidth(400);
            }
        });

        HomePage homePage = null;
        SearchPage searchPage = new SearchPage(root);
        searchB.getStyleClass().add("active");
        currentPage = searchPage;

        homeB.setOnAction(actionEvent -> {
            currentPage = homePage == null ? new HomePage() : homePage;
            root.getChildren().setAll(menu, currentPage);
            homeB.getStyleClass().add("active");
        });
        searchB.setOnAction(actionEvent -> {
            currentPage = searchPage == null ? new SearchPage() : searchPage;
            root.getChildren().setAll(menu, currentPage);
            searchB.getStyleClass().add("active");
        });

        return menu;
    }

    public VBox getProfileContent(boolean logedIn) {
        Button b1 = new Button(logedIn ? "Abmelden" : "Anmelden/Registrieren");
        Button b2 = new Button("Einstellung");

        b1.setOnAction(actionEvent -> {
            showLogInFields(false);
        });

        VBox vBox = new VBox(b1, b2);

        vBox.getStyleClass().add("profileMenu");

        for (Button b : List.of(b1, b2)) {
            b.addEventHandler(ActionEvent.ACTION, actionEvent -> {
                vBox.setVisible(false);
            });
        }

        return vBox;
    }

    public void showLogInFields(boolean signUp) {
        VBox vBox = new VBox(20);
        vBox.setStyle("-fx-alignment: CENTER;");

        TextField name = new TextField();
        name.setPromptText("Bentzername");
        PasswordField password = new PasswordField();
        password.setPromptText("Passwort");
        name.getStyleClass().add("userName");
        password.getStyleClass().add("password");

        vBox.getChildren().addAll(name, password);

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        ButtonType logInBT = new ButtonType(signUp ? "Registrieren" : "Anmelden", ButtonBar.ButtonData.APPLY);
        dialog.getDialogPane().getButtonTypes().addAll(logInBT, ButtonType.CANCEL);

        Button logInB = (Button) dialog.getDialogPane().lookupButton(logInBT);
        logInB.setDisable(true);
        logInB.disableProperty().bind(name.textProperty().isEmpty().or(password.textProperty().isEmpty()));

        logInB.setOnAction(actionEvent -> {
            System.out.println("jo");
            actionEvent.consume();
        });

        dialog.setHeaderText(signUp ? "Registrierung" : "Anmeldung");
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/logIn.css").toExternalForm());
        dialog.getDialogPane().setContent(vBox);
        dialog.getDialogPane().setPrefSize(600, 400);
        dialog.getDialogPane().getStyleClass().add("logInDialog");

        Platform.runLater(() -> name.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == logInBT) {
                return new Pair<>(name.getText(), password.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            System.out.println(result.getKey() + " : " + result.getValue());
        });
    }

    public static Integer getUserId() {
        return userId;
    }

    private String getUsername() {
        return "";
    }

    public static void setUserId(Integer userId) {
        Main.userId = userId;
    }

    public static void main(String[] args) {
        launch(args);
    }
}