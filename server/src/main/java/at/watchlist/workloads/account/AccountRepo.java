package at.watchlist.workloads.account;

import at.watchlist.db.entities.Account;

import java.util.List;

public interface AccountRepo {
    List<Account> getAll();
    Account get(Long id);
    void add(Account account);
    void remove(Account account);
    void update(Account account);
    boolean usernameExists(String s);
}
