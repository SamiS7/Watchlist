package at.watchlist.workloads.movie;

import at.watchlist.entities.MovieInfos;
import at.watchlist.entities.Watchlist;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
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
                Sort.by("time").descending(), accountId);
        return getMovieInfos(query, start, end);

    }

    public List<MovieInfos> getSeen(Long accountId, int start, int end) {
        var query = watchlistRepo.find("account_id = ?1 and seen = true",
                Sort.by("time").descending(), accountId);
        return getMovieInfos(query, start, end);
    }

    public List<MovieInfos> getNotSeen(Long accountId, int start, int end) {
        var query = watchlistRepo.find("account_id = ?1 and seen = false",
                Sort.by("time").descending(), accountId);
        return getMovieInfos(query, start, end);
    }

    private List<MovieInfos> getMovieInfos(PanacheQuery<Watchlist> query, int start, int end) {
        if (end > -1) {
            query.range(start, end);
        }
        return query.stream().map(l -> l.getMovieId().getMovieInfos()).toList();
    }
}
