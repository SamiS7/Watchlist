package watchlist;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import watchlist.forServer.models.Account;
import watchlist.request.LogIn;
import watchlist.ui.components.AlertError;
import watchlist.ui.pages.HomePage;
import watchlist.ui.pages.SearchPage;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {
    private static LongProperty userId = new SimpleLongProperty(-1);
    private static Account account;
    private final Integer width = 1200;
    private final Integer height = 800;
    private Node currentPage;
    private static HBox root = new HBox();
    private static String serverUrl = "http://localhost:8080";

    @Override
    public void start(Stage stage) {
        initBody();

        userId.addListener(observable -> initBody());

        root.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Watchlist");

        LogIn.initObjectMapper();

        stage.show();
    }

    public void initBody() {
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
        Button searchB = new Button("Suchen");

        VBox v1 = new VBox(homeB, searchB);

        ImageView profileIV = new ImageView(new Image(this.getClass().getResourceAsStream("/icons/profile.png")));
        profileIV.setPreserveRatio(true);
        profileIV.fitWidthProperty().bind(menu.widthProperty().multiply(0.7));

        Button profileB = new Button();
        profileB.getStyleClass().add("profileB");
        profileB.setGraphic(profileIV);

        VBox profileContent = getProfileContent();
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

        AtomicReference<HomePage> homePage = new AtomicReference<>(new HomePage());
        AtomicReference<SearchPage> searchPage = new AtomicReference<>();
        if (currentPage == null) {
            changeCurrentPage(new HomePage(), menu);
        }

        homeB.setOnAction(actionEvent -> {
            changeCurrentPage(homePage.get(), menu);
        });
        searchB.setOnAction(actionEvent -> {
            if (searchPage.get() == null) {
                searchPage.set(new SearchPage());
            }
            changeCurrentPage(searchPage.get(), menu);
        });

        homeB.setOnMouseClicked(action -> {
            if (action.getButton().equals(MouseButton.PRIMARY) && action.getClickCount() == 2) {
                var v = new HomePage();
                homePage.set(v);
                changeCurrentPage(v, menu);
            }
        });

        searchB.setOnMouseClicked(action -> {
            if (action.getButton().equals(MouseButton.PRIMARY) && action.getClickCount() == 2) {
                var v = new SearchPage();
                searchPage.set(v);
                changeCurrentPage(v, menu);
            }
        });
    }

    public void changeCurrentPage(Node newPage, Node menu) {
        if (currentPage != null) {
            currentPage.getStyleClass().remove("active");
        }
        newPage.getStyleClass().add("active");
        currentPage = newPage;
        root.getChildren().setAll(menu, currentPage);
    }

    public VBox getProfileContent() {
        Button b1 = new Button(userId.get() != -1 ? "Abmelden" : "Anmelden/Registrieren");
        Button b2 = new Button("Einstellung");

        VBox vBox = new VBox(b1, b2);

        if (userId.get() > -1) {
            Label username = new Label("User: " + this.account.getUsername());
            username.getStyleClass().add("usernameL");
            vBox.getChildren().add(0, username);
        }


        vBox.getStyleClass().add("profileMenu");

        b1.setOnAction(actionEvent -> {
            if (userId.get() == -1) {
                showLogInDialog(false, vBox);
            } else {
                userId.set(-1);
            }
        });

        for (Button b : List.of(b1, b2)) {
            b.addEventHandler(ActionEvent.ACTION, actionEvent -> {
                vBox.setVisible(false);
            });
        }

        return vBox;
    }

    public void showLogInDialog(boolean signUp, VBox profileMenu) {
        VBox vBox = new VBox(20);
        vBox.setStyle("-fx-alignment: CENTER;");

        TextField name = new TextField("Sami");
        name.setPromptText("Bentzername");
        PasswordField password = new PasswordField();
        password.setText("passw");
        password.setPromptText("Passwort");
        Button signUpB = new Button(!signUp ? "Zum Registrieren" : "Zum Anmelden");
        signUpB.getStyleClass().add("signUpB");
        name.getStyleClass().add("userName");
        password.getStyleClass().add("password");

        vBox.getChildren().addAll(name, password, signUpB);

        Dialog<Account> dialog = new Dialog<>();
        ButtonType logInBT = new ButtonType(signUp ? "Registrieren" : "Anmelden", ButtonBar.ButtonData.APPLY);
        ButtonType okBT = ButtonType.OK;
        ButtonType cancelBT = ButtonType.CANCEL;
        dialog.getDialogPane().getButtonTypes().addAll(logInBT, okBT, cancelBT);

        Button logInB = (Button) dialog.getDialogPane().lookupButton(logInBT);
        logInB.getStyleClass().add("dialogB");
        logInB.setDisable(true);
        logInB.disableProperty().bind(name.textProperty().isEmpty().or(password.textProperty().isEmpty()));

        Button cancelB = (Button) dialog.getDialogPane().lookupButton(cancelBT);
        cancelB.getStyleClass().add("dialogB");
        Button okB = (Button) dialog.getDialogPane().lookupButton(okBT);
        okB.getStyleClass().add("dialogB");

        dialog.setHeaderText(signUp ? "Registrierung" : "Anmeldung");
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/logIn.css").toExternalForm());
        dialog.getDialogPane().setContent(vBox);
        dialog.getDialogPane().setPrefSize(600, 400);
        dialog.getDialogPane().getStyleClass().add("logInDialog");

        Platform.runLater(() -> name.requestFocus());

        dialog.show();

        signUpB.setOnAction(actionEvent -> {
            dialog.close();
            showLogInDialog(!signUp, profileMenu);
        });

        dialog.setResultConverter(buttonType -> buttonType.equals(logInBT) ? new Account(name.getText().trim(), password.getText().trim()) : null);

        logInB.setOnAction(actionEvent -> {
            try {
                Account account = dialog.getResult();

                String url = serverUrl + "/account";
                if (signUp) {
                    url += "/signUp";
                } else {
                    url += "/logIn";
                }
                var response = LogIn.logIn(url, account);

                if (response.getStatus() == 404 && !response.getBody().isSuccess()) {
                    Label errorL = new Label();
                    errorL.getStyleClass().add("errorLabel");
                    errorL.setText(response.getBody().getErrorMsg());
                    int i = vBox.getChildren().size() - 1;
                    if (vBox.getChildren().get(i) instanceof Label) {
                        vBox.getChildren().set(i, errorL);
                    } else {
                        vBox.getChildren().add(errorL);
                    }
                    dialog.show();
                } else {
                    this.account = response.getBody().getAccount();
                    userId.set(this.account.getId());
                }

            } catch (UnirestException e) {
                e.printStackTrace();
                new AlertError("Technische Probleme!", "Es sind technische Probleme aufgetreten. Versuchen es erneut!");
            }
        });

        logInB.fireEvent(new ActionEvent());
    }

    public static LongProperty userIdProperty() {
        return userId;
    }

    public static Account getAccount() {
        return account;
    }

    public static String getServerUrl() {
        return serverUrl;
    }

    public static HBox getRoot() {
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}