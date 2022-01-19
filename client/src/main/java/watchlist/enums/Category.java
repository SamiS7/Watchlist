package watchlist.enums;

public enum Category {
    SHORTLY_SAVED("Kürzlich gespeichert"),
    NOT_SEEN("Noch nicht gesehen"),
    SEEN ("Schon Gesehen"),
    FAMOUS("Beliebt");

    private String titel;

    Category(String titel) {
        this.titel = titel;
    }

    public String getTitel() {

        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
}
