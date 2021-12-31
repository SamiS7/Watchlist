package at.watchlist.workloads.account;

import at.watchlist.db.entities.Account;
import at.watchlist.models.AccountDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AccountServiceImpl implements AccountService {
    @Inject
    private AccountRepoImpl accountRepo;

    public AccountServiceImpl(AccountRepoImpl accountRepo) {
        this.accountRepo = accountRepo;
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
        if (!accountRepo.usernameExists(accountDTO.getUsername())) {
            Account account = new Account(accountDTO.getUsername(), accountDTO.getPassword());
            accountRepo.add(account);
            return account;
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
    public boolean update(Account account) {
        if (accountRepo.get(account.getId()) != null && !accountRepo.usernameExists(account.getUsername())) {
            accountRepo.update(account);
            return true;
        }
        return false;
    }

    public AccountRepoImpl getAccountRepo() {
        return accountRepo;
    }
}
