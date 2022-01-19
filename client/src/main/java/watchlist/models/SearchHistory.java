package watchlist.models;

import java.time.LocalDateTime;

public class SearchHistory {
    private Long id;
    private Account account;
    private String searchStr;
    private LocalDateTime time;

    public SearchHistory() {
    }

    public SearchHistory(Account account, String searchStr) {
        this.id = id;
        this.account = account;
        this.searchStr = searchStr;
        this.time = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
