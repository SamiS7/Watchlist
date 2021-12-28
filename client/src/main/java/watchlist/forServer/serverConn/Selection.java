package watchlist.forServer.serverConn;

import watchlist.forServer.serverClass.Poster;

import java.util.List;

public class Selection {
    private static Selection selection;

    public Selection() {
    }

    public static Selection getSelection() {
        return selection != null ? selection : new Selection();
    }


    public List<Poster> getShortlyAdded(int userId, int limit) {
        return null;
    }

    public List<Poster> getWatchlist(int userId) {
        return null;
    }

    public List<Poster> getWatchedMovies(int userId, int limit) {
        return null;
    }

    public List<Poster> getNotWatchedMovies(int userId, int limit) {
        return null;
    }

    public List<Poster> getBestRated(int limit) {
        return null;
    }

    public boolean isSaved(String id, Integer userId) {
        return false;
    }
}
