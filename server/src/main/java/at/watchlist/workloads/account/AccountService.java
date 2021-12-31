package at.watchlist.workloads.account;

import at.watchlist.db.entities.Account;
import at.watchlist.models.AccountDTO;

import java.util.List;

public interface AccountService {
    List<Account> getAll();
    Account get(Long id);
    Account add(AccountDTO accountDTO);
    boolean remove(Long id);
    boolean update(Account account);
}
