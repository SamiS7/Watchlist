package at.watchlist.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SearchHistory {
    @EmbeddedId
    private SearchHistoryId searchHistoryId;
    private LocalDateTime time;

    public SearchHistory() {
    }

    public SearchHistory(SearchHistoryId searchHistoryId) {
        this.searchHistoryId = searchHistoryId;
    }

    public SearchHistory(SearchHistoryId searchHistoryId, LocalDateTime time) {
        this.searchHistoryId = searchHistoryId;
        this.time = time;
    }

    public SearchHistoryId getSearchHistoryId() {
        return searchHistoryId;
    }

    public void setSearchHistoryId(SearchHistoryId searchHistoryId) {
        this.searchHistoryId = searchHistoryId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
