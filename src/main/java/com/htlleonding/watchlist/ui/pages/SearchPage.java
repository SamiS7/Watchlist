package com.htlleonding.watchlist.ui.pages;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.htlleonding.watchlist.db.dbclass.MovieInfos;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchPage extends VBox {

    public SearchPage() {
        initSearchBox();
    }

    public void initSearchBox() {
        HBox hb = new HBox();
        hb.getStyleClass().add("searchBox");
        TextField textField = new TextField();
        textField.setPromptText("Suchen ...");
        Button button = new Button("Suchen");
        hb.getChildren().addAll(textField, button);
        this.getChildren().add(hb);
        this.getStyleClass().add("content");
        this.setSpacing(20);

        button.setOnAction(actionEvent -> {
            String searchStr = textField.getText();
            if (searchStr.length() > 0) {
                ScrollPane scrollPane = new ScrollPane(search(searchStr));
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                scrollPane.setFitToWidth(true);
                scrollPane.getStyleClass().add("scrollbar");
                this.getChildren().add(scrollPane);
            }
        });


    }

    public TilePane search(String searchWord) {
        TilePane tilePane = new TilePane();
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        tilePane.setTileAlignment(Pos.CENTER);

        JsonObject jsonObject = request("https://imdb-api.com/en/API/Search/k_46caativ/" + searchWord);
        for (JsonElement j : jsonObject.getAsJsonArray("results")) {
            Image poster = new Image(((JsonObject) j).get("image").getAsString());
            ImageView imageView = new ImageView(poster);
            imageView.setFitWidth(100 * 2.36);
            imageView.setFitHeight(100 * 3.21);
            Button button = new Button();
            button.setGraphic(imageView);
            tilePane.getChildren().add(button);

            button.setOnAction(actionEvent -> {
                showMovieDetail(((JsonObject) j).get("id").getAsString());
            });
        }
        return tilePane;
    }

    private JsonObject request(String urlStr) {
        Task<JsonObject> task = new Task<JsonObject>() {
            @Override
            protected JsonObject call() throws Exception {
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    int responseCode = conn.getResponseCode();

                    if (responseCode != 200) {
                        return null;
                    }
                    {
                        StringBuilder informationString = new StringBuilder();
                        Scanner scanner = new Scanner(url.openStream());

                        while (scanner.hasNext()) {
                            informationString.append(scanner.nextLine());
                        }
                        scanner.close();
                        return JsonParser.parseString(String.valueOf(informationString)).getAsJsonObject();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        return task.getValue();
    }

    private void showMovieDetail(String pId) {
        JsonObject jo = request("https://imdb-api.com/en/API/Title/k_46caativ/" + pId);
        var id = jo.get("id").getAsString();
        var title = jo.get("title").getAsString();
        var year = jo.get("year").getAsString();
        var plot = jo.get("plot").getAsString();
        var type = jo.get("type").getAsString();
        var genres = jo.get("genres").getAsString();
        var casting = jo.get("stars").getAsString();
        var poster = jo.get("image").getAsString();
        var rating = jo.get("imDbRating").getAsDouble();
        MovieInfos mi = new MovieInfos(id, title, year, plot, type, genres, casting, poster, rating);

        MovieDetail movieDetail = new MovieDetail(mi);
        this.getChildren().setAll(movieDetail);
    }
}