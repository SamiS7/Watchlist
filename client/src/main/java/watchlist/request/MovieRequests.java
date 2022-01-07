package watchlist.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import watchlist.Main;
import watchlist.forServer.models.MovieInfos;
import watchlist.forServer.models.SavedMovie;

import java.io.InputStream;

public class MovieRequests {

    public static HttpResponse<InputStream> saveMovie(MovieInfos movieInfos, Long account) throws UnirestException {
        String url = Main.getServerUrl() + "/account/" + account + "/movie";
        var response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .body(movieInfos);
        return response.asBinary();
    }

    public static HttpResponse<InputStream> removeMovie(String movieId, Long account) throws UnirestException {
        String url = Main.getServerUrl() + "/account/" + account + "/movie";
        var response = Unirest.delete(url)
                .header("Content-Type", "application/json")
                .body(movieId);
        return response.asBinary();
    }

    public static HttpResponse<SavedMovie> getSavedMovie(String movieId, Long account) throws UnirestException {
        String url = Main.getServerUrl() + "/account/" + account + "/" + movieId;
        var response = Unirest.get(url);
        return response.asObject(SavedMovie.class);
    }
}
