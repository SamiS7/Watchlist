package com.htlleonding.watchlist.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class DBM {
    private EntityManager entityManager;
    private static DBM dbm;

    private DBM() {
        entityManager = getEntityManager();
    }

    public static DBM getDbm() {
        return dbm == null ? new DBM() : dbm;
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("java");

        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }


    public List<MovieInfos> getShortlyAdded(String id, int limit) {
        Query result = entityManager.createQuery("from" + MovieInfos.class.getName() + " where id = ");

    }


}
