package at.watchlist.db;

import at.watchlist.db.dbclass.MovieInfos;
import at.watchlist.db.dbclass.SavedMovie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

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
        Query query = entityManager.createQuery("from " + MovieInfos.class.getName() + " mi join " + SavedMovie.class.getName() + " sm on(mi.id = sm.movieId.movieId) where sm.movieId.accountId = :aId order by sm.time desc");
        query.setParameter("aId", id);

        return getPosterData(query.getResultStream().limit(limit).toList());
    }

    public List<Poster> getWatchlist(int userId) {
        Query query = entityManager.createQuery("from " + MovieInfos.class.getName() + " mi join " + SavedMovie.class.getName() + " sm on(mi.id = sm.movieId.movieId) where sm.movieId.accountId = :aId");
        return getPosterData(query.getResultList());
    }

    public List<Poster> getWatchedMovies(int userId, int limit) {
        Query query = entityManager.createQuery("from " + MovieInfos.class.getName() + " mi join " + SavedMovie.class.getName() + " sm on(mi.id = sm.movieId.movieId) where sm.movieId.accountId = :aId and sm.seen");
        return getPosterData(query.getResultStream().limit(limit).toList());
    }

    public List<Poster> getNotWatchedMovies(int userId, int limit) {
        Query query = entityManager.createQuery("from " + MovieInfos.class.getName() + " mi join " + SavedMovie.class.getName() + " sm on(mi.id = sm.movieId.movieId) where sm.movieId.accountId = :aId and not sm.seen");
        return getPosterData(query.getResultStream().limit(limit).toList());
    }

    public List<Poster> getBestRated(int limit) {
        Query query = entityManager.createQuery("from " + MovieInfos.class.getName() + " order by rating desc");
        return getPosterData(query.getResultStream().limit(limit).toList());
    }

    public List<Poster> getPosterData(List<MovieInfos> movieInfos) {
        List<Poster> list = new LinkedList<>();
        for(MovieInfos m:movieInfos) {
            list.add(new Poster(m.getPosterUrl() ,m.getId(), m.getTitle()));
        }
        return list;
    }
}