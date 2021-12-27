package at.watchlist.db;

import at.watchlist.db.dbclass.MovieInfos;
import at.watchlist.db.dbclass.SavedMovie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DAO {
    private EntityManager entityManager;
    private static DAO instance;

    public DAO() {
        entityManager = getEntityManager();
    }

    public static DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
        }

        return instance;
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("java");

        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public void persist(MovieInfos movieInfos) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(movieInfos);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    public void delete(MovieInfos movieInfos) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(movieInfos);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    public boolean isSaved(String movieId, Integer accountId) {
        Query query = entityManager.createQuery("from " + SavedMovie.class.getName() + "  where accountId = :aId and movieId = :mId");
        query.setParameter("aId", accountId);
        query.setParameter("mId", movieId);

        return query.getResultStream().toList().size() > 0;
    }
}
