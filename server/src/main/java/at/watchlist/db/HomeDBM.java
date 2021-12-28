package at.watchlist.db;

import at.watchlist.db.entities.MovieInfos;
import at.watchlist.db.entities.SavedMovie;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeDBM {
    private EntityManager entityManager;
    private static HomeDBM dbm;

    private HomeDBM() {
        entityManager = getEntityManager();
    }

    public static HomeDBM getDbm() {
        return dbm == null ? new HomeDBM() : dbm;
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("java");

        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }


    public List<Poster> getShortlyAdded(int id, int limit) {
        TypedQuery query = entityManager.createQuery("from " + MovieInfos.class.getName() + " mi join " + SavedMovie.class.getName() + " sm on(mi.id = sm.movieId.movieId) where sm.movieId.accountId = :aId order by sm.time desc", MovieInfos.class);
        query.setParameter("aId", id);

        return getPosterData(query, limit);
    }

    public List<Poster> getWatchlist(int userId) {
        TypedQuery query = entityManager.createQuery("from " + MovieInfos.class.getName() + " mi join " + SavedMovie.class.getName() + " sm on(mi.id = sm.movieId.movieId) where sm.movieId.accountId = :aId", MovieInfos.class);
        return getPosterData(query, -1);
    }

    public List<Poster> getWatchedMovies(int userId, int limit) {
        TypedQuery query = entityManager.createQuery("from " + MovieInfos.class.getName() + " mi join " + SavedMovie.class.getName() + " sm on(mi.id = sm.movieId.movieId) where sm.movieId.accountId = :aId and sm.seen", MovieInfos.class);
        return getPosterData(query, limit);
    }

    public List<Poster> getNotWatchedMovies(int userId, int limit) {
        TypedQuery query = entityManager.createQuery("from " + MovieInfos.class.getName() + " mi join " + SavedMovie.class.getName() + " sm on(mi.id = sm.movieId.movieId) where sm.movieId.accountId = :aId and not sm.seen", MovieInfos.class);
        return getPosterData(query, limit);
    }

    public List<Poster> getBestRated(int limit) {
        TypedQuery query = entityManager.createQuery("from " + MovieInfos.class.getName() + " order by rating desc", MovieInfos.class);
        return getPosterData(query, limit);
    }

    public List<Poster> getPosterData(TypedQuery<MovieInfos> query, int limit) {
        List<Poster> list = new LinkedList<>();
        for(MovieInfos m:query.getResultStream().limit(limit).collect(Collectors.toList())) {
            list.add(new Poster(m.getPosterUrl() ,m.getId(), m.getTitle()));
        }
        return list;
    }
}