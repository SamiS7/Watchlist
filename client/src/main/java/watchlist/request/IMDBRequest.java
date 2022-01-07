package watchlist.request;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import javafx.concurrent.Task;

public class IMDBRequest {


    public static Task<JsonObject> request(String urlStr) {
        Task<JsonObject> task = new Task<JsonObject>() {
            @Override
            protected JsonObject call() throws Exception {
                try {
                    HttpResponse response = Unirest.get(urlStr).asString();
                    return JsonParser.parseString(String.valueOf(response.getBody())).getAsJsonObject();
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
    }

    public static Task<JsonObject> requestWithRapidApi(String urlStr) {
        Task<JsonObject> task = new Task<JsonObject>() {
            @Override
            protected JsonObject call() throws Exception {
                HttpResponse<String> response = Unirest.get(urlStr)
                        .header("x-rapidapi-host", "imdb-internet-movie-database-unofficial.p.rapidapi.com")
                        .header("x-rapidapi-key", "afa7f04a6dmshf059dfa93a77e3fp188a98jsna20ee4c9b5c7")
                        .asString();
                return JsonParser.parseString(String.valueOf(response.getBody())).getAsJsonObject();
            }
        };
        
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        return task;
    }
}
