package at.watchlist.workloads.search;

import at.watchlist.entities.SearchWord;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SearchWordRepo implements PanacheRepositoryBase<SearchWord, String> {
}
