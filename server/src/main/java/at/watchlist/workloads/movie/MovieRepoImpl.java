package at.watchlist.workloads.movie;

import at.watchlist.entities.MovieInfos;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MovieRepoImpl implements PanacheRepositoryBase<MovieInfos, String> {
}
