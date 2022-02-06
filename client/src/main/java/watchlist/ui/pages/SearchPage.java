package watchlist.ui.pages;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import watchlist.request.IMDBRequest;
import watchlist.ui.components.AlertError;

public class SearchPage extends VBox implements Reloadable {
    private ScrollPane scrollPane;
    private String searchStr;

    public SearchPage() {
        addAndInit();
    }

    public SearchPage(String searchStr) {
        this();
        this.searchStr = searchStr;
    }

    @Override
    public void initBody() {
        this.getChildren().clear();

        HBox hb = new HBox();
        hb.getStyleClass().add("searchBox");
        TextField textField = new TextField();
        textField.setPromptText("Suchen ...");

        Button button = new Button("Suchen");
        hb.getChildren().addAll(textField, button);

        textField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                button.fireEvent(new ActionEvent());
            }
        });

        this.getChildren().add(hb);
        this.getStyleClass().add("content");
        this.setSpacing(20);

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().addAll("scrollPane", "content");
        this.getChildren().add(scrollPane);

        button.setOnAction(actionEvent -> {
            this.searchStr = textField.getText();
            if (searchStr.length() > 0) {
                scrollPane.setContent(search());
            }
        });

        if (searchStr != null) {
            textField.setText(searchStr);
            button.fireEvent(new ActionEvent());
        } else {
            Platform.runLater(() -> textField.requestFocus());
        }
    }

    public TilePane search() {
        TilePane tilePane = new TilePane();
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        tilePane.setTileAlignment(Pos.CENTER);
        Label statusL = new Label("Wird gesucht ...");
        statusL.getStyleClass().add("searching");
        tilePane.getChildren().add(statusL);

        Task<JsonObject> taskJson = IMDBRequest.request(IMDBRequest.imdbSearchUrl + searchStr);
        //Task<JsonObject> taskJson = IMDBRequest.requestWithRapidApi(IMDBRequest.rapidApiSearchUrl + searchStr);

        taskJson.setOnSucceeded(action -> {
            new Thread(() -> {
                try {
                    double w = tilePane.getWidth() / 236;
                    w = (tilePane.getWidth() - (25 * w)) / 236;
                    double ww = ((tilePane.getWidth() - (25 * w)) / Math.round(w)) - 10;
                    double dh = (ww - 236) * 1.36;

                    var arr = taskJson.get().getAsJsonArray("results");
                    //var arr = taskJson.get().getAsJsonArray("titles");

                    Platform.runLater(() -> tilePane.getChildren().clear());
                    if (arr.size() > 0) {
                        for (JsonElement j : arr) {
                            Image poster = new Image(((JsonObject) j).get("image").getAsString());
                            ImageView imageView = new ImageView(poster);

                            imageView.setFitWidth(ww);
                            imageView.setFitHeight(100 * 3.21 + dh);
                            Button button = new Button();
                            button.setGraphic(imageView);
                            button.setBackground(null);
                            button.setOnAction(actionEvent ->
                                    MovieDetail.showMovieDetail(((JsonObject) j).get("id").getAsString(), this)
                            );
                            Platform.runLater(() -> tilePane.getChildren().add(button));
                        }
                    } else {
                        Label msgL = new Label("Kein Treffer gefunden!");
                        msgL.getStyleClass().add("msgL");
                        Platform.runLater(() -> tilePane.getChildren().add(msgL));
                    }
                } catch (Exception e) {
                    new AlertError("Technische Probleme", " Es sind technische Probleme aufgetreten. Versuchen es erneut!");
                    e.printStackTrace();
                }
            }).start();
        });
        return tilePane;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }
}
