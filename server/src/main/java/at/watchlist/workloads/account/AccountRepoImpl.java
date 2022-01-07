package at.watchlist.workloads.account;

import at.watchlist.entities.Account;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountRepoImpl implements PanacheRepository<Account> {

    public Account findByName(String username) {
        return find("username", username).stream().findFirst().orElse(null);
    }

    public boolean usernameExists(String s) {
        return findByName(s) != null;
    }
}
