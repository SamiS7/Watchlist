package watchlist.ui.pages;

import watchlist.Main;

public interface Page {

    void initBody();

    default void addReloadEvent() {
        Main.userIdProperty().addListener(observable -> initBody());
    }
}
