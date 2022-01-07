package at.watchlist.workloads.account;

import at.watchlist.entities.Account;
import at.watchlist.entities.MovieInfos;
import at.watchlist.entities.SavedMovie;
import at.watchlist.models.AccountDTO;
import at.watchlist.models.LogInModel;

import java.util.List;

public interface AccountService {
    List<Account> getAll();

    Account get(Long id);

    LogInModel add(AccountDTO accountDTO);

    LogInModel logIn(AccountDTO accountDTO);

    boolean addMovie(Long accountId, MovieInfos movieInfos);

    boolean removeSavedMovie(Long accountId, String MovieId);

    SavedMovie getSavedMovie(Long accountId, String movieId);
}
