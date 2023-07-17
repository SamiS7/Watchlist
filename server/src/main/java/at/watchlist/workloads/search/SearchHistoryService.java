package at.watchlist.workloads.search;

import at.watchlist.entities.SearchHistoryId;
import at.watchlist.entities.SearchWord;
import at.watchlist.workloads.account.AccountRepoImpl;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class SearchHistoryService {
    @Inject
    private SearchHistoryRepo searchHistoryRepo;
    @Inject
    private SearchWordRepo searchWordRepo;
    @Inject
    private AccountRepoImpl accountRepo;

    public List<SearchWord> get(Long accountId) {
        var query = searchHistoryRepo.find("account_id", Sort.by("time").descending(), accountId)
                .range(0, 9);
        if (query.count() <= 0) {
            var q = searchWordRepo.findAll(Sort.by("counter").descending())
                    .range(0, 9);
            return q.stream().toList();
        } else {
            return query.stream().map(s -> s.getSearchHistoryId().getSearchWord()).toList();
        }
    }

    public boolean update(Long accountId, String searchStr) {
        if (searchStr.length() <= 0) {
            return false;
        }
        var sw = searchWordRepo.findById(searchStr);
        var a = accountRepo.findById(accountId);

        if (a != null) {
            if (sw == null) {
                sw = new SearchWord(searchStr);
            }
            sw.incrementCounter();
            searchWordRepo.persist(sw);

            a.addSearchHistory(sw, searchHistoryRepo.findById(new SearchHistoryId(a, sw)) != null);
            accountRepo.persist(a);
            return true;
        }
        return false;
    }
}
