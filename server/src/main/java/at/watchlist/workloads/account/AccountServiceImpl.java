package at.watchlist.workloads.account;

import at.watchlist.db.entities.Account;
import at.watchlist.db.entities.MovieInfos;
import at.watchlist.models.AccountDTO;
import at.watchlist.workloads.movie.MovieServiceImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AccountServiceImpl implements AccountService {
    @Inject
    private AccountRepoImpl accountRepo;
    @Inject
    private MovieServiceImpl movieService;

    public AccountServiceImpl(AccountRepoImpl accountRepo, MovieServiceImpl movieService) {
        this.accountRepo = accountRepo;
        this.movieService = movieService;
    }

    @Override
    public List<Account> getAll() {
        return accountRepo.getAll();
    }

    @Override
    public Account get(Long id) {
        return accountRepo.get(id);
    }

    @Override
    public Account add(AccountDTO accountDTO) {
        if (accountDTO != null) {
            accountDTO.setPassword(BCrypt.hashpw(accountDTO.getPassword(), BCrypt.gensalt()));
            Account account = new Account(accountDTO.getUsername(), accountDTO.getPassword());
            if (validAccount(account)) {
                accountRepo.add(account);
                return account;
            }
        }

        return null;
    }

    @Override
    public boolean remove(Long id) {
        Account account = accountRepo.get(id);
        if (account != null) {
            accountRepo.remove(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Account account, String oldPassword) {
        if (oldPassword.length() >= 5 && BCrypt.checkpw(oldPassword, accountRepo.get(account.getId()).getPassword())) {
                account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt()));
        } else if (oldPassword.length() > 0) {
            return false;
        }

        if (account != null && validAccount(account)) {
            accountRepo.update(account);
            return true;
        }

        return false;
    }

    @Override
    public boolean addMovie(Long accountId, MovieInfos movieInfos) {
        Account account = accountRepo.get(accountId);

        if (account != null && movieInfos != null) {
            movieService.add(movieInfos);
            account.addMovies(movieInfos);
            accountRepo.update(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeSavedMovie(Long accountId, String movieId) {
        Account account = accountRepo.get(accountId);
        MovieInfos movieInfos = movieService.get(movieId);

        if (account != null && movieInfos != null) {
            account.removeMovie(movieInfos);
            accountRepo.update(account);
            return true;
        }
        return false;
    }

    public static boolean checkLength(String s1, String s2) {
        return s1.length() >= 3 && s2.length() >= 5;
    }

    public boolean validAccount(Account account) {
        return checkLength(account.getUsername(), account.getPassword()) && !accountRepo.usernameExists(account.getUsername());
    }
}
