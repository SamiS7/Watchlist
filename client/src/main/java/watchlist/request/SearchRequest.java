package watchlist.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import watchlist.Main;
import watchlist.models.SearchWord;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchRequest {
    public static List<SearchWord> getSearchWords() throws UnirestException {
        String url = Main.getServerUrl() + "/searchHistory/" + Main.userIdProperty().get();
        var request = Unirest.get(url);
        var response = request.asObject(SearchWord[].class);
        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

    public static HttpResponse<InputStream> updateSearchHistory(String str) throws UnirestException {
        String url = Main.getServerUrl() + "/searchHistory/" + Main.userIdProperty().get();
        var request = Unirest.put(url)
                .header("Content-Type", "application/json")
                .body(str);
        return request.asBinary();
    }
}
