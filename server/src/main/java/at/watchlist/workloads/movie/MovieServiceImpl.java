package at.watchlist.workloads.movie;

import at.watchlist.db.entities.MovieInfos;

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
        return movieRepo.getAll();
    }

    @Override
    public MovieInfos get(String id) {
        return movieRepo.get(id);
    }

    @Override
    public boolean add(MovieInfos movieInfos) {
        if (movieRepo.get(movieInfos.getId()) == null) {
            movieRepo.add(movieInfos);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(MovieInfos movieInfos) {
        if (movieInfos != null) {
            movieRepo.update(movieInfos);
            return true;
        }
        return false;
    }
}
