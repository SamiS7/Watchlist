package at.watchlist.workloads.movie;

import at.watchlist.entities.MovieInfos;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MovieServiceImpl {
    @Inject
    private MovieRepoImpl movieRepo;

    public MovieServiceImpl(MovieRepoImpl movieRepo) {
        this.movieRepo = movieRepo;
    }

    public List<MovieInfos> getAll() {
        return movieRepo.findAll().list();
    }

    public MovieInfos get(String id) {
        return movieRepo.findById(id);
    }

    public boolean add(MovieInfos movieInfos) {
        if (movieRepo.findById(movieInfos.getId()) == null) {
            movieRepo.persist(movieInfos);
            return true;
        }
        return false;
    }

    public boolean update(MovieInfos movieInfos) {
        if (movieInfos != null) {
            movieRepo.getEntityManager().merge(movieInfos);
            return true;
        }
        return false;
    }

    public List<MovieInfos> getFamous(int start, int end) {
        var query = movieRepo.findAll(Sort.by("imdbRating").descending());
        if (end > -1) {
            query.range(start, end);
        }
        return query.stream().toList();
    }
}
