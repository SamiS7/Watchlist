package com.htlleonding.watchlist.ui.pages;

import com.google.gson.*;
import com.htlleonding.watchlist.db.dbclass.MovieInfos;
import com.htlleonding.watchlist.ui.components.MovieInfoForImdbO;
import com.htlleonding.watchlist.ui.components.MovieInfoForImdbU;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import java.util.concurrent.ExecutionException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

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

        //JsonObject jsonObject = request("https://imdb-api.com/en/API/Search/k_46caativ/" + searchWord);
        JsonObject jsonObject = request("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/search/" + searchWord);
        //for (JsonElement j : jsonObject.getAsJsonArray("results")) {
        for (JsonElement j : jsonObject.getAsJsonArray("titles")) {
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
        /*
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

        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
         */

        return requestWithRapidApi(urlStr);
    }

    private JsonObject requestWithRapidApi(String urlStr) {
        try {
            HttpResponse<String> response = Unirest.get(urlStr)
                    .header("x-rapidapi-host", "imdb-internet-movie-database-unofficial.p.rapidapi.com")
                    .header("x-rapidapi-key", "afa7f04a6dmshf059dfa93a77e3fp188a98jsna20ee4c9b5c7")
                    .asString();
            //return JsonParser.parseString(String.valueOf(response.getBody())).getAsJsonObject();
            return JsonParser.parseString(String.valueOf(response.getBody())).getAsJsonObject();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T asMovieInfo(JsonObject jsonObject, Class<T> classOfT) {
        return new Gson().fromJson(jsonObject, classOfT);
    }

    private void showMovieDetail(String pId) {
        //JsonObject jo = request("https://imdb-api.com/en/API/Title/k_46caativ/" + pId + "/trailer");
        JsonObject jo = request("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/film/" + pId);

        //MovieInfoForImdbO m = asMovieInfo(jo, MovieInfoForImdbO.class);
        MovieInfoForImdbU m = asMovieInfo(jo, MovieInfoForImdbU.class);
        MovieDetail movieDetail = new MovieDetail(convertToMovieInfo(m));
        this.getChildren().setAll(movieDetail);
    }
    
    private MovieInfos convertToMovieInfo(Object o) {
        if (o instanceof MovieInfoForImdbO m) {
            return new MovieInfos(m.getId(), m.getTitle(), m.getYear(), m.getPlot(), m.getType(), m.getGenres(),
                    m.getStars(), m.getImage(), m.getTrailer().getLink(), m.getTrailer().getThumbnailUrl(), m.getImDbRatin());
        } else if (o instanceof MovieInfoForImdbU m) {
            return new MovieInfos(m.getId(), m.getTitle(), m.getYear(), m.getPlot(), null, null,
                    null, m.getPoster(), m.getTrailer().getLink(), m.getTrailer().getThumbnailUrl(), m.getRating());
        }
        return null;
    }
}