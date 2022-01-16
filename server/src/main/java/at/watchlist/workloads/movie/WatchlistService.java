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
        var list = watchlistRepo.find("account_id", Sort.by("time"), accountId).range(start, end).stream();
        return list.map(l -> l.getMovieId().getMovieInfos()).toList();

    }
}
