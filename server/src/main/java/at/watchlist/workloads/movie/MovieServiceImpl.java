package at.watchlist.workloads.movie;

import at.watchlist.entities.MovieInfos;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MovieServiceImpl implements MovieService{
    @Inject
    private MovieRepoImpl movieRepo;

    public MovieServiceImpl(MovieRepoImpl movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public List<MovieInfos> getAll() {
        return movieRepo.findAll().list();
    }

    @Override
    public MovieInfos get(String id) {
        return movieRepo.findById(id);
    }

    @Override
    public boolean add(MovieInfos movieInfos) {
        if (movieRepo.findById(movieInfos.getId()) == null) {
            movieRepo.persist(movieInfos);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(MovieInfos movieInfos) {
        if (movieInfos != null) {
            movieRepo.getEntityManager().merge(movieInfos);
            return true;
        }
        return false;
    }

    public List<MovieInfos> getFamous(int start, int end) {
        var query = movieRepo.findAll(Sort.by("imdbRating").descending()).range(start, end);
        return query.stream().toList();
    }
}
