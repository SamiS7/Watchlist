package watchlist.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import watchlist.Main;
import watchlist.forServer.models.MovieInfos;

import java.io.InputStream;

public class SaveMovie {

    public static HttpResponse<InputStream> saveMovie(MovieInfos movieInfos, Long account, boolean save) throws UnirestException {
        String url = Main.getServerUrl() + "/account/" + account + "/movie";
        var response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .body(movieInfos);
        return response.asBinary();
    }

    public static HttpResponse<InputStream> removeMovie(String movieId, Long account, boolean save) throws UnirestException {
        String url = Main.getServerUrl() + "{" + account + "}/movie";
        var response =Unirest.post(url)
                .header("accept", "application/json")
                .body(movieId);
        return response.asBinary();
    }
}
