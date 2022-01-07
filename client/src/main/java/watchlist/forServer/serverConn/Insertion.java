package watchlist.forServer.serverConn;

import watchlist.forServer.models.MovieInfos;

public class Insertion {
    private static Insertion INSTANCE;

    public Insertion() {
    }

    public static Insertion getINSTANCE() {
        return INSTANCE != null ? INSTANCE : new Insertion();
    }

    public void persist(MovieInfos movieInfos) {
    }

    public void delete(MovieInfos movieInfos) {
    }
}
