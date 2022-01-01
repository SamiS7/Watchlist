package at.watchlist.workloads.movie;

import at.watchlist.db.entities.MovieInfos;

import java.util.List;

public interface MovieService {
    List<MovieInfos> getAll();
    MovieInfos get(String id);
    boolean add(MovieInfos movieInfos);
    boolean update(MovieInfos movieInfos);
}
