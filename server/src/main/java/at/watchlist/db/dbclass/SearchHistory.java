package at.watchlist.db.dbclass;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn()
    private Account account;
    private LocalDateTime time;

    public SearchHistory() {
    }

    public SearchHistory(Long id, Account account, LocalDateTime time) {
        this.id = id;
        this.account = account;
        this.time = time;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
