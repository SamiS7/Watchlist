package watchlist.forServer.serverConn;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import watchlist.Main;
import watchlist.forServer.models.MovieInfos;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Selection {
    private static Selection INSTANCE;
    private Long userId = Main.userIdProperty().get();

    public Selection() {
    }

    public static Selection getINSTANCE() {
        return INSTANCE != null ? INSTANCE : new Selection();
    }


    public List<MovieInfos> getShortlyAdded(int start, int limit) throws UnirestException {
        String url = Main.getServerUrl() + "/movie/" + Main.userIdProperty().get() + "/shortlyAdded/" + start + "/" + start + limit;
        var request = Unirest.get(url);
        List<MovieInfos> list = new ArrayList<>();
        var response = request.asObject(MovieInfos[].class);
        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

    public List<MovieInfos> getWatchlist() {
        return null;
    }

    public List<MovieInfos> getWatchedMovies(int start, int limit) {
        return null;
    }

    public List<MovieInfos> getNotWatchedMovies(int start, int limit) {
        return null;
    }

    public List<MovieInfos> getBestRated(int start, int limit) {
        return null;
    }

}
