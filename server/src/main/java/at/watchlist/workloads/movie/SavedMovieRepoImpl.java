package at.watchlist.workloads.movie;

import at.watchlist.entities.MovieId;
import at.watchlist.entities.SavedMovie;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SavedMovieRepoImpl implements PanacheRepositoryBase<SavedMovie, MovieId> {
}
