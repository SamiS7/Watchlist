package at.watchlist.workloads.account;

import at.watchlist.db.entities.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class AccountRepoImpl implements AccountRepo {
    @Inject
    private EntityManager entityManager;

    public AccountRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Account> getAll() {
        return entityManager.createQuery("select a from Account a", Account.class).getResultList();
    }

    @Override
    public Account get(Long id) {
        TypedQuery<Account> query = entityManager.createQuery("select a from Account a where a.id = :id", Account.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public void add(Account account) {
        entityManager.persist(account);
    }

    @Override
    public void remove(Account account) {
        entityManager.remove(account);
    }

    @Override
    public void update(Account account) {
        entityManager.merge(account);
    }

    @Override
    public boolean usernameExists(String s) {
        Query query = entityManager.createQuery("select a from Account a where a.username = :username");
        query.setParameter("username", s);
        return query.getResultList().size() > 0;
    }
}
