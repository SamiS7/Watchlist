package at.watchlist;

import at.watchlist.ui.pages.HomePage;
import at.watchlist.ui.pages.SearchPage;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static Integer userId = 0;
    private Integer width = 900;
    private Integer height = 600;
    private Node currentPage;
    private StackPane root = new StackPane();
    private HBox content = new HBox();

    @Override
    public void start(Stage stage) {
        VBox menu = getMenu(stage);

        root.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        content.getChildren().addAll(menu, currentPage);
        root.getChildren().add(content);
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

        VBox v1 = new VBox(homeB, myWatchlistB, seenB, notSeenB, famousB, searchB);

        ImageView profileIV = new ImageView(new Image(this.getClass().getResourceAsStream("/icons/profile.png")));
        profileIV.setPreserveRatio(true);
        profileIV.fitWidthProperty().bind(menu.widthProperty().multiply(0.7));

        Button profileB = new Button();
        profileB.getStyleClass().add("profileB");
        profileB.setGraphic(profileIV);

        VBox v2 = new VBox(profileB);
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

        VBox profileContent = getProfileContent(false);
        profileB.setOnMouseClicked(e -> {
            double mx = e.getSceneX();
            double my = e.getSceneY();

            if (root.getChildren().contains(profileContent)) {
                root.getChildren().remove(profileContent);
            } else {
                root.getChildren().add(profileContent);
            }

            double cx = profileContent.getLayoutX();
            double cy = profileContent.getLayoutY();

            double tx = cx - mx;
            double ty = my > cy ? (my - cy) : (cy - my);
            ty -= 300;

            profileContent.setTranslateY(ty);
            profileContent.setTranslateX(-tx);
        });

        profileB.focusedProperty().addListener((observable -> {
            if (root.getChildren().contains(profileContent)) {
                root.getChildren().remove(profileContent);
            }
        }));

        HomePage homePage = null;
        SearchPage searchPage = new SearchPage(content);
        currentPage = searchPage;

        homeB.setOnAction(actionEvent -> {
            currentPage = homePage == null ? new HomePage() : homePage;
            content.getChildren().setAll(menu, currentPage);
        });
        searchB.setOnAction(actionEvent -> {
            currentPage = searchPage == null ? new SearchPage() : searchPage;
            content.getChildren().setAll(menu, currentPage);
        });

        return menu;
    }

    public VBox getProfileContent(boolean logedIn) {
        Button b1 = new Button(logedIn ? "Abmelden" : "Anmelden/Registrieren");
        Button b2 = new Button("Einstellung");

        b1.setOnAction(actionEvent -> {
            root.getChildren().add(getLogInFields(false));
        });


        VBox vBox = new VBox(b1,b2);

        vBox.getStyleClass().add("profileMenu");

        return vBox;
    }

    public Node getLogInFields(boolean signUp) {
        VBox vBox = new VBox(20);
        vBox.setPrefSize(2000,1500);
        vBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-alignment: CENTER;");

        TextField name = new TextField();
        name.setPromptText("Bentzername");
        PasswordField password = new PasswordField();
        password.setPromptText("Passwort");
        name.getStyleClass().add("userName");
        password.getStyleClass().add("password");

        Button logIn = new Button(signUp ? "Registrieren" : "Anmelden");
        logIn.getStyleClass().add("logInB");

        Button clearButton = new Button("Zurück");
        clearButton.getStyleClass().add("logInhideB");

        if (!signUp) {
            Button signUpB = new Button("Registrieren");
            signUpB.getStyleClass().add("logInB");
            vBox.getChildren().addAll(name, password, logIn, signUpB, clearButton);

            signUpB.setOnAction(actionEvent -> {
                root.getChildren().remove(vBox);
                root.getChildren().add(getLogInFields(true));
            });
        } else {
            vBox.getChildren().addAll(name, password, logIn, clearButton);
        }

        clearButton.setOnAction(action -> {
            root.getChildren().remove(vBox);
        });

        return vBox;
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