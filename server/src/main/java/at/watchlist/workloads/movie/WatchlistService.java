package at.watchlist.workloads.movie;

import at.watchlist.entities.MovieInfos;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class WatchlistService {
    @Inject
    private WatchlistRepoImpl watchlistRepo;

    public List<MovieInfos> getShortlyAdded(Long accountId, int start, int end) {
        var query = watchlistRepo.find("account_id",
                Sort.by("time").descending(), accountId).range(start, end).stream();
        return query.map(l -> l.getMovieId().getMovieInfos()).toList();

    }

    public List<MovieInfos> getSeen(Long accountId, int start, int end) {
        var query = watchlistRepo.find("account_id = ?1 and seen = true",
                Sort.by("time").descending(), accountId).range(start, end).stream();
        return query.map(l -> l.getMovieId().getMovieInfos()).toList();
    }

    public List<MovieInfos> getNotSeen(Long accountId, int start, int end) {
        var query = watchlistRepo.find("account_id = ?1 and seen = false",
                Sort.by("time").descending(), accountId).range(start, end).stream();
        return query.map(l -> l.getMovieId().getMovieInfos()).toList();
    }
}
