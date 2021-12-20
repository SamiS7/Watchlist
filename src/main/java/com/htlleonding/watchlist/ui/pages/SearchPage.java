package com.htlleonding.watchlist.ui.pages;

import com.google.gson.*;
import com.htlleonding.watchlist.db.dbclass.MovieInfos;
import com.htlleonding.watchlist.ui.components.MovieInfoForImdbO;
import com.htlleonding.watchlist.ui.components.MovieInfoForImdbU;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class SearchPage extends VBox {
    private ScrollPane scrollPane;
    private String searchStr;
    private Node root;

    public SearchPage() {
        initSearchBox();
    }

    public SearchPage(String searchStr, Node root) {
        this.searchStr = searchStr;
        this.root = root;
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

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scrollPane");
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
        }
    }

    public TilePane search() {
        TilePane tilePane = new TilePane();
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        tilePane.setTileAlignment(Pos.CENTER);

        //Task<JsonObject> taskJson = request("https://imdb-api.com/en/API/Search/k_46caativ/" + searchStr);
        Task<JsonObject> taskJson = requestWithRapidApi("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/search/" + searchStr);

        taskJson.setOnSucceeded(action -> {
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    try {
                        //for (JsonElement j : taskJson.get().getAsJsonArray("results")) {
                        for (JsonElement j : taskJson.get().getAsJsonArray("titles")) {
                            Image poster = new Image(((JsonObject) j).get("image").getAsString());
                            ImageView imageView = new ImageView(poster);

                            double w = tilePane.getWidth() / 236;
                             w = (tilePane.getWidth() - (20 * w)) / 236;
                            double ww = ((tilePane.getWidth() - (20 * 3)) / Math.round(w)) - 10;
                            double dh = (ww - 236) * 1.36;

                            imageView.setFitWidth(ww);
                            imageView.setFitHeight(100 * 3.21 + dh);
                            Button button = new Button();
                            button.setGraphic(imageView);
                            button.setBackground(null);
                            button.setOnAction(actionEvent -> {
                                showMovieDetail(((JsonObject) j).get("id").getAsString());
                            });
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    tilePane.getChildren().add(button);
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }
            };

            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
        });
        return tilePane;
    }

    private Task<JsonObject> request(String urlStr) {

        Task<JsonObject> task = new Task<JsonObject>() {
            @Override
            protected JsonObject call() throws Exception {
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    int responseCode = conn.getResponseCode();

                    InputStream response = conn.getInputStream();

                    if (responseCode != 200) {
                        return null;
                    } else {
                        StringBuilder informationString = new StringBuilder();
                        Scanner scanner = new Scanner(response);

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
        return task;

        //return requestWithRapidApi(urlStr);
    }

    private Task<JsonObject> requestWithRapidApi(String urlStr) {
        Task<JsonObject> task = new Task<JsonObject>() {
            @Override
            protected JsonObject call() throws Exception {
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
        };


        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        return task;
    }

    private <T> T asMovieInfo(JsonObject jsonObject, Class<T> classOfT) {
        return new Gson().fromJson(jsonObject, classOfT);
    }

    private void showMovieDetail(String pId) {
        //Task<JsonObject> jo = request("https://imdb-api.com/en/API/Title/k_46caativ/" + pId + "/trailer");

        Task<JsonObject> jo = requestWithRapidApi("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/film/" + pId);


        jo.setOnSucceeded(action -> {
            //MovieInfoForImdbO m = asMovieInfo(jo, MovieInfoForImdbO.class);
           /*
            MovieInfoForImdbO m = null;
            try {
                m = asMovieInfo(jo.get(), MovieInfoForImdbO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            MovieInfoForImdbU m = null;
            try {
                m = asMovieInfo(jo.get(), MovieInfoForImdbU.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            MovieDetail movieDetail = new MovieDetail(convertToMovieInfo(m), this.getWidth(), this.getHeight());

            ((HBox) root).getChildren().remove(this);
            ((HBox) root).getChildren().add(movieDetail);

        });
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

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }
}
