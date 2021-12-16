module com.htlleonding.watchlist {
    requires javafx.controls;
    requires jakarta.persistence;
    requires com.google.gson;
    requires unirest.java;

    exports com.htlleonding.watchlist;
    exports com.htlleonding.watchlist.db;
    exports com.htlleonding.watchlist.db.dbclass;
    opens com.htlleonding.watchlist.db;
    opens com.htlleonding.watchlist.db.dbclass;
    opens com.htlleonding.watchlist;
    opens com.htlleonding.watchlist.ui.components;
}