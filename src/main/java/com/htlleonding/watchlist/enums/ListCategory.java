package com.htlleonding.watchlist.enums;

public enum ListCategory {
    SHORTLY_SAVED("Kürzlich gespeichert"),
    RECOMMENDED("Empfohlen"),
    NOT_SEEN("Noch nicht gesehen"),
    SEEN ("Schon Gesehen"),
    FAMOUS("Beliebt"),
    MY_WATCHLIST("Meine Merkliste");

    private String titel;

    ListCategory(String titel) {
        this.titel = titel;
    }

    public String getTitel() {

        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
}
