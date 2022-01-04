package at.watchlist.workloads.account;

import at.watchlist.db.entities.Account;
import at.watchlist.db.entities.MovieInfos;
import at.watchlist.models.AccountDTO;
import at.watchlist.models.LogInModel;
import org.graalvm.collections.Pair;

import java.util.List;

public interface AccountService {
    List<Account> getAll();

    Account get(Long id);

    LogInModel add(AccountDTO accountDTO);

    LogInModel logIn(AccountDTO accountDTO);

    boolean remove(Long id);

    String update(Account account, String oldPassword);

    boolean addMovie(Long accountId, MovieInfos movieInfos);

    boolean removeSavedMovie(Long accountId, String movieId);
}