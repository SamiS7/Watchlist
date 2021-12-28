module client {
    requires javafx.controls;
    requires javafx.web;
    requires com.google.gson;
    requires unirest.java;

    exports watchlist;
    opens watchlist;
    opens watchlist.ui.components;
}