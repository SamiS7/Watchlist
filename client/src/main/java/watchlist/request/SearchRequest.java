package watchlist.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import watchlist.Main;
import watchlist.models.SearchWord;
import watchlist.ui.components.AlertError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchRequest {
    public static List<SearchWord> getSearchWords()  {
        String url = Main.getServerUrl() + "/searchHistory/" + Main.userIdProperty().get();
        var request = Unirest.get(url);
        HttpResponse<SearchWord[]> response = null;
        try {
            response = request.asObject(SearchWord[].class);
            return Arrays.stream(response.getBody()).collect(Collectors.toList());
        } catch (UnirestException e) {
            alert();
        }
        return null;
    }

    public static void updateSearchHistory(String str) {
        String url = Main.getServerUrl() + "/searchHistory/" + Main.userIdProperty().get();
        var request = Unirest.put(url)
                .header("Content-Type", "application/json")
                .body(str);
        try {
            request.asBinary();
        } catch (UnirestException e) {
            alert();
        }
    }

    private static void alert() {
        new AlertError("Technische Probleme!", "Es sind technische Probleme aufgetreten. Versuchen es erneut!");
    }
}
