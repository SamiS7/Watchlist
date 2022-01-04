package watchlist.forServer.serverConn;

import watchlist.Main;
import watchlist.forServer.models.MovieInfos;

import java.util.List;

public class Selection {
    private static Selection selection;
    private Long userId = Main.getUserId();

    public Selection() {
    }

    public static Selection getSelection() {
        return selection != null ? selection : new Selection();
    }


    public List<MovieInfos> getShortlyAdded( int limit) {
        return null;
    }

    public List<MovieInfos> getWatchlist() {
        return null;
    }

    public List<MovieInfos> getWatchedMovies( int limit) {
        return null;
    }

    public List<MovieInfos> getNotWatchedMovies( int limit) {
        return null;
    }

    public List<MovieInfos> getBestRated(int limit) {
        return null;
    }

    public boolean isSaved(String id) {
        return false;
    }
}
