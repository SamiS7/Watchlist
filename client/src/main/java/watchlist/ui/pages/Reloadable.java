package watchlist.ui.pages;

import watchlist.Main;

public interface Reloadable {

    void initBody();

    default void addReloadEvent() {
        Main.userIdProperty().addListener(observable -> initBody());
    }

    default void addAndInit() {
        addReloadEvent();
        initBody();
    }
}
