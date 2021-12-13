module com.htlleonding.watchlist {
    requires javafx.controls;
    requires jakarta.persistence;
    requires com.google.gson;
    requires unirest.java;

    exports com.htlleonding.watchlist;
    exports com.htlleonding.watchlist.ui.pages;
    exports com.htlleonding.watchlist.ui.components;
    opens com.htlleonding.watchlist.ui.components;
}