package com.htlleonding.watchlist.db;

import com.htlleonding.watchlist.db.dbclass.MovieInfos;
import com.htlleonding.watchlist.db.dbclass.SavedMovie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

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

        return query.getResultStream().toList().size() > 0 ? true : false;
    }
}
