package watchlist.models;

public class SearchWord {
    private String searchWord;
    private Long counter;

    public SearchWord() {
    }

    public SearchWord(String searchWord) {
        this.searchWord = searchWord;
        this.counter = 0l;
    }

    public SearchWord(String searchWord, Long counter) {
        this.searchWord = searchWord;
        this.counter = counter;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public void incrementCounter() {
        this.counter++;
    }
}
