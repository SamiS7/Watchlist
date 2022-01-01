package at.watchlist.workloads.movie;

import at.watchlist.db.entities.MovieInfos;

import java.util.List;

public interface MovieRepo {
    List<MovieInfos> getAll();
    MovieInfos get(String id);
    void add(MovieInfos movieInfos);
    void update(MovieInfos movieInfos);
}
