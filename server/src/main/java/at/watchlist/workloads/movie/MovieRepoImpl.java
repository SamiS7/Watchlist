package at.watchlist.workloads.movie;

import at.watchlist.db.entities.MovieInfos;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class MovieRepoImpl implements MovieRepo{
    @Inject
    private EntityManager entityManager;

    public MovieRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<MovieInfos> getAll() {
        return entityManager.createQuery("select m from MovieInfos m").getResultList();
    }

    @Override
    public MovieInfos get(String id) {
        TypedQuery<MovieInfos> query = entityManager.createQuery("select m from MovieInfos m where m.id = :id", MovieInfos.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public void add(MovieInfos movieInfos) {
        entityManager.persist(movieInfos);
    }

    @Override
    public void update(MovieInfos movieInfos) {
        entityManager.merge(movieInfos);
    }
}
