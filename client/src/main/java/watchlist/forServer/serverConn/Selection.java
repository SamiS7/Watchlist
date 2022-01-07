package watchlist.forServer.serverConn;

import watchlist.Main;
import watchlist.forServer.models.MovieInfos;

import java.util.List;

public class Selection {
    private static Selection INSTANCE;
    private Long userId = Main.userIdProperty().get();

    public Selection() {
    }

    public static Selection getINSTANCE() {
        return INSTANCE != null ? INSTANCE : new Selection();
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
