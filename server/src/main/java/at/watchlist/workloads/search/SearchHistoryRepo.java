package at.watchlist.workloads.search;

import at.watchlist.entities.SearchHistory;
import at.watchlist.entities.SearchHistoryId;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SearchHistoryRepo implements PanacheRepositoryBase<SearchHistory, SearchHistoryId> {


}
