package at.watchlist.workloads.account;

import at.watchlist.entities.Account;
import at.watchlist.entities.MovieInfos;
import at.watchlist.entities.Watchlist;
import at.watchlist.models.AccountDTO;
import at.watchlist.models.LogInModel;

import java.util.List;

public interface AccountService {
    List<Account> getAll();

    Account get(Long id);

    LogInModel add(AccountDTO accountDTO);

    LogInModel logIn(AccountDTO accountDTO);

    boolean addMovie(Long accountId, MovieInfos movieInfos);

    boolean updateWatchlist(Watchlist watchlist);

    boolean removeSavedMovie(Long accountId, String MovieId);

    Watchlist getWatchlist(Long accountId, String movieId);
}
