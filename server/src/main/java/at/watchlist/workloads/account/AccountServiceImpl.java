package at.watchlist.workloads.account;

import at.watchlist.entities.Account;
import at.watchlist.entities.MovieId;
import at.watchlist.entities.MovieInfos;
import at.watchlist.entities.SavedMovie;
import at.watchlist.models.AccountDTO;
import at.watchlist.models.LogInModel;
import at.watchlist.workloads.movie.MovieRepoImpl;
import at.watchlist.workloads.movie.MovieServiceImpl;
import at.watchlist.workloads.movie.SavedMovieRepoImpl;
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
    @Inject
    MovieRepoImpl movieRepo;
    @Inject
    private SavedMovieRepoImpl savedMovieRepo;

    public AccountServiceImpl(AccountRepoImpl accountRepo, MovieServiceImpl movieService, MovieRepoImpl movieRepo, SavedMovieRepoImpl savedMovieRepo) {
        this.accountRepo = accountRepo;
        this.movieService = movieService;
        this.movieRepo = movieRepo;
        this.savedMovieRepo = savedMovieRepo;
    }

    @Override
    public List<Account> getAll() {
        var r = accountRepo.findAll().list();
        r.stream().forEach(account -> account.setPassword(null));
        return r;
    }

    @Override
    public Account get(Long id) {
        var a = accountRepo.findById(id);
        a.setPassword(null);
        return a;
    }

    @Override
    public LogInModel add(AccountDTO accountDTO) {
        if (accountDTO != null) {
            String s = validAccount(accountDTO);
            if (s.length() <= 0) {
                Account account = new Account(accountDTO.getUsername(), accountDTO.getPassword());
                account.setPassword(BCrypt.hashpw(accountDTO.getPassword(), BCrypt.gensalt()));
                accountRepo.persist(account);

                Account a = copyAccount(account);
                return giveSuccessLogInModel(a, s);
            } else return giveUnSuccessLogInModel(s);
        }

        return giveUnSuccessLogInModel("Der Username muss mind. 3 und Passwort 5 Zeichen haben!");
    }

    @Override
    public LogInModel logIn(AccountDTO accountDTO) {

        if (accountDTO == null) {
            return giveUnSuccessLogInModel("Eingabenfelder dürfen nicht leer sein!");
        }
        if (!checkLength(accountDTO.getUsername(), accountDTO.getPassword())) {
            return giveUnSuccessLogInModel("Der Username muss mind. 3 und Passwort 5 Zeichen haben!");
        }

        Account account = accountRepo.findByName(accountDTO.getUsername());
        if (account != null && checkPassword(accountDTO.getPassword(), account.getPassword())) {
            return giveSuccessLogInModel(copyAccount(account), "");
        }
        return giveUnSuccessLogInModel("Der Username oder das Password ist falsch!");
    }

    @Override
    public boolean remove(Long id) {
        Account account = accountRepo.findById(id);
        if (account != null) {
            accountRepo.delete(account);
            return true;
        }
        return false;
    }

    @Override
    public String update(Account account, String oldPassword) {
        if (oldPassword.length() >= 5) {
            if (checkPassword(oldPassword, account.getId())) {
                account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt()));
            } else {
                return "Das alte Passwort stimmt nicht!";
            }
        } else if (oldPassword.length() > 0) {
            return "Das Passwort muss mind 5 Zeichen haben!";
        }
        String v = validAccount(account);
        if (account != null || v.length() <= 0) {
            accountRepo.getEntityManager().merge(account);
            return v;
        }

        return v;
    }

    @Override
    public boolean addMovie(Long accountId, MovieInfos movieInfos) {
        Account account = accountRepo.findById(accountId);

        if (account != null && movieInfos != null) {
            if (movieService.get(movieInfos.getId()) == null) {
                movieService.add(movieInfos);
            }
            account.addMovies(movieInfos);
            accountRepo.persist(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeSavedMovie(Long accountId, String movieId) {
        Account account = accountRepo.findById(accountId);
        MovieInfos movieInfos = movieService.get(movieId);

        if (account != null && movieInfos != null) {
            MovieId movieId1 = new MovieId(account, movieInfos);
            SavedMovie savedMovie = savedMovieRepo.findById(movieId1);
            if (savedMovie != null) {
                account.removeMovie(savedMovie);
                savedMovieRepo.delete(savedMovie);
                accountRepo.persist(account);
                return true;
            }
        }
        return false;
    }

    public static boolean checkLength(String s1, String s2) {
        return (s1.length() >= 3 && s1.length() <= 20) && (s2.length() >= 5 && s2.length() <= 255);
    }

    public String validAccount(Account account) {
        if (!checkLength(account.getUsername(), account.getPassword())) {
            return "Der Username muss mind. 3 und Passwort 5 Zeichen haben!";
        }
        if (accountRepo.usernameExists(account.getUsername())) {
            return "Der Username " + account.getUsername() + " existiert bereits!";
        }
        return "";
    }

    public String validAccount(AccountDTO accountDTO) {
        return validAccount(new Account(accountDTO.getUsername(), accountDTO.getPassword()));
    }

    public Account copyAccount(Account account) {
        Account a = new Account();
        a.setId(account.getId());
        a.setUsername(account.getUsername());

        return a;
    }

    public LogInModel giveSuccessLogInModel(Account account, String s) {
        return new LogInModel(account, s, true);
    }

    public LogInModel giveUnSuccessLogInModel(String s) {
        return new LogInModel(null, s);
    }

    public boolean checkPassword(String given, Long id) {
        return checkPassword(given, accountRepo.findById(id).getPassword());
    }

    public boolean checkPassword(String given, String password) {
        return BCrypt.checkpw(given, password);
    }
}
