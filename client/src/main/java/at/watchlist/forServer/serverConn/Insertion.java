package at.watchlist.forServer.serverConn;

import at.watchlist.forServer.serverClass.MovieInfos;

public class Insertion {
    private static Insertion insertion;

    public Insertion() {
    }

    public static Insertion getInsertion() {
        return insertion != null ? insertion : new Insertion();
    }

    public void persist(MovieInfos movieInfos) {
    }

    public void delete(MovieInfos movieInfos) {
    }
}
