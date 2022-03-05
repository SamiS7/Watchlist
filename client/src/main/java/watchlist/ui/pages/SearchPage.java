package watchlist.ui.pages;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import watchlist.models.SearchWord;
import watchlist.request.IMDBRequest;
import watchlist.request.SearchRequest;
import watchlist.ui.components.AlertError;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SearchPage extends StackPane implements Reloadable {
    private ScrollPane scrollPane;
    private SimpleStringProperty searchStr = new SimpleStringProperty("");

    public SearchPage() {
        addAndInit();
    }

    public SearchPage(String searchStr) {
        this();
        this.searchStr.set(searchStr);
    }

    @Override
    public void initBody() {
        this.getChildren().clear();

        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("searchBox");
        searchBox.setMinHeight(60);

        TextField textField = new TextField();
        textField.setPromptText("Suchen ...");
        textField.textProperty().bindBidirectional(this.searchStr);

        Button button = new Button("Suchen");
        textField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                button.fireEvent(new ActionEvent());
            }
        });
        AtomicReference<VBox> suggestions = new AtomicReference<>(getSuggestions(textField));

        VBox textFieldBox = new VBox(textField, suggestions.get());
        textFieldBox.getStyleClass().add("textFieldBox");

        searchBox.getChildren().addAll(textFieldBox, button);

        this.getChildren().add(searchBox);
        StackPane.setAlignment(searchBox, Pos.TOP_CENTER);
        searchBox.setMaxHeight(70);
        this.getStyleClass().add("content");

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().addAll("scrollPane");

        this.getChildren().add(scrollPane);
        scrollPane.toBack();
        scrollPane.maxHeightProperty().bind(this.heightProperty().subtract(90));
        StackPane.setAlignment(scrollPane, Pos.BOTTOM_CENTER);

        button.setOnAction(actionEvent -> {
            if (searchStr.get().length() > 0) {
                scrollPane.setContent(search());
                textFieldBox.getChildren().remove(suggestions.get());
                suggestions.set(getSuggestions(textField));
                textFieldBox.getChildren().add(suggestions.get());
            }
        });

        if (searchStr.get() != null) {
            button.fireEvent(new ActionEvent());
        } else {
            Platform.runLater(() -> textField.requestFocus());
        }
    }

    public TilePane search() {
        TilePane tilePane = new TilePane();
        tilePane.setVgap(10);
        tilePane.setHgap(0);
        tilePane.setTileAlignment(Pos.CENTER);
        tilePane.setAlignment(Pos.CENTER);
        Label statusL = new Label("Wird gesucht ...");
        statusL.getStyleClass().add("searching");
        tilePane.getChildren().add(statusL);

        Task<JsonObject> taskJson = IMDBRequest.request(IMDBRequest.imdbSearchUrl + this.searchStr.get());
        //Task<JsonObject> taskJson = IMDBRequest.requestWithRapidApi(IMDBRequest.rapidApiSearchUrl + searchStr.get());

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
                            Image poster = new Image(((JsonObject) j).get("image").getAsString(), ww, 100 * 3.21 + dh, false, false);
                            ImageView imageView = new ImageView(poster);
                            //imageView.setFitWidth(ww);
                            //imageView.setFitHeight(100 * 3.21 + dh);

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
                    Platform.runLater(() -> new AlertError("Technische Probleme", " Es sind technische Probleme aufgetreten. Versuchen es erneut!"));
                }
            }).start();
        });

        SearchRequest.updateSearchHistory(this.searchStr.get());

        return tilePane;
    }

    private VBox getSuggestions(TextField textField) {
        VBox vBox = new VBox();
        vBox.getStyleClass().add("suggestionBox");
        vBox.setFocusTraversable(true);
        vBox.setVisible(false);

        textField.focusedProperty().addListener((observableValue, oldVal, newVal) ->
                vBox.setVisible(newVal || vBox.isFocused() || vBox.getChildren().stream().anyMatch(b -> b.isFocused()))
        );

        vBox.managedProperty().bind(vBox.visibleProperty());

        List<SearchWord> list = SearchRequest.getSearchWords();

        if (list != null) {
            for (SearchWord s : list) {
                Button b = new Button(s.getSearchWord());
                b.managedProperty().bind(b.visibleProperty());
                vBox.getChildren().add(b);

                b.setVisible(s.getSearchWord().contains(textField.getText()));
                textField.textProperty().addListener((observableValue, s1, t1) -> {
                    b.setVisible(s.getSearchWord().contains(t1));
                });

                b.setOnAction(actionEvent -> {
                    textField.setText(s.getSearchWord());
                    textField.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
                });
            }
        }

        return vBox;
    }

    public String getSearchStr() {
        return searchStr.get();
    }

    public void setSearchStr(String searchStr) {
        this.searchStr.set(searchStr);
    }
}
