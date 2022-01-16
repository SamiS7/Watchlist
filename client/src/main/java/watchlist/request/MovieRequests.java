package watchlist.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import watchlist.Main;
import watchlist.forServer.models.MovieInfos;
import watchlist.forServer.models.Watchlist;

import java.io.InputStream;

public class MovieRequests {

    public static HttpResponse<InputStream> saveMovie(MovieInfos movieInfos, Long account) throws UnirestException {
        String url = Main.getServerUrl() + "/account/" + account + "/movie";
        var request = Unirest.post(url)
                .header("Content-Type", "application/json")
                .body(movieInfos);
        return request.asBinary();
    }

    public static HttpResponse<InputStream> removeMovie(String movieId, Long account) throws UnirestException {
        String url = Main.getServerUrl() + "/account/" + account + "/movie";
        var request = Unirest.delete(url)
                .header("Content-Type", "application/json")
                .body(movieId);
        return request.asBinary();
    }

    public static HttpResponse<InputStream> updateMovie(Watchlist watchlist) throws UnirestException {
        String url = Main.getServerUrl() + "/account/movie";
        var request = Unirest.put(url)
                .header("Content-Type", "application/json")
                .body(watchlist);
        return request.asBinary();
    }

    public static HttpResponse<Watchlist> getSavedMovie(String movieId, Long account) throws UnirestException {
        String url = Main.getServerUrl() + "/account/" + account + "/" + movieId;
        var request = Unirest.get(url);
        return request.asObject(Watchlist.class);
    }
}
