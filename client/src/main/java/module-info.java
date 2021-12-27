module client {
    requires javafx.controls;
    requires javafx.web;
    requires com.google.gson;
    requires unirest.java;

    exports at.watchlist;
    opens at.watchlist;
    opens at.watchlist.ui.components;
}