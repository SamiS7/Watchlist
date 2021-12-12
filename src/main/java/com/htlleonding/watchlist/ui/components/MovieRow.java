package com.htlleonding.watchlist.ui.components;

import com.htlleonding.watchlist.db.HomeDBM;
import com.htlleonding.watchlist.db.Poster;
import com.htlleonding.watchlist.enums.ListCategory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class MovieRow extends VBox {
    private ListCategory listCategory;
    private int userId;
    private int limit;

    //region constructor, getter & setter
    public MovieRow(ListCategory listCategory, int userId, int limit) {
        this.listCategory = listCategory;
        this.userId = userId;
        this.limit = limit;
    }

    public ListCategory getListCategory() {
        return listCategory;
    }

    public void setListCategory(ListCategory listCategory) {
        this.listCategory = listCategory;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    //endregion

    public void initBox() {
        Text titel = new Text(listCategory.getTitel());
        this.getChildren().addAll(titel, getContent());
    }

    public HBox getContent() {
        HBox h = new HBox();
        List<Poster> movieData = getMovieData();

        for (Poster m : movieData) {
            h.getChildren().add(new ImageView(m.getImage()));
        }
        return h;
    }

    public List<Poster> getMovieData() {
        return switch (listCategory) {
            case SHORTLY_SAVED -> HomeDBM.getDbm().getShortlyAdded(userId, limit);
            case MY_WATCHLIST -> HomeDBM.getDbm().getWatchlist(userId);
            case SEEN -> HomeDBM.getDbm().getWatchedMovies(userId, limit);
            case NOT_SEEN -> HomeDBM.getDbm().getNotWatchedMovies(userId, limit);
            case FAMOUS -> HomeDBM.getDbm().getBestRated(limit);
        };
    }
}
