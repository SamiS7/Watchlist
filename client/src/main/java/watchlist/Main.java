package watchlist;

import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import watchlist.models.Account;
import watchlist.request.LogIn;
import watchlist.ui.components.Menu;
import watchlist.ui.pages.HomePage;

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
        currentPage = new HomePage();
        root.getChildren().setAll(new Menu(currentPage), currentPage);

        root.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Watchlist");

        LogIn.initObjectMapper();

        stage.show();
    }

    public static LongProperty userIdProperty() {
        return userId;
    }

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        Main.account = account;
        userId.set(account.getId());
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