package at.watchlist.entities;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SearchHistoryId implements Serializable {
    @JsonbTransient
    @ManyToOne
    private Account account;
    @ManyToOne
    private SearchWord searchWord;

    public SearchHistoryId() {
    }

    public SearchHistoryId(Account account, SearchWord searchWord) {
        this.account = account;
        this.searchWord = searchWord;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public SearchWord getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(SearchWord searchWord) {
        this.searchWord = searchWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHistoryId that = (SearchHistoryId) o;
        return account.equals(that.account) && searchWord.equals(that.searchWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, searchWord);
    }
}
