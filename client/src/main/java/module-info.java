module client {
    requires javafx.controls;
    requires javafx.web;
    requires com.google.gson;
    requires unirest.java;
    requires com.fasterxml.jackson.databind;
    requires httpclient;

    exports watchlist;
    exports watchlist.forServer.models;

    opens watchlist;
    opens watchlist.ui.components;
}