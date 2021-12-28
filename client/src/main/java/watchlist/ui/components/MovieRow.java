package watchlist.ui.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import watchlist.enums.ListCategory;
import watchlist.forServer.serverClass.Poster;
import watchlist.forServer.serverConn.Selection;

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
        initBox();
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
            Image image = new Image(m.getImageUrl());
            h.getChildren().add(new ImageView(image));
        }
        return h;
    }

    public List<Poster> getMovieData() {
        return switch (listCategory) {
            case SHORTLY_SAVED -> Selection.getSelection().getShortlyAdded(userId, limit);
            case MY_WATCHLIST -> Selection.getSelection().getWatchlist(userId);
            case SEEN -> Selection.getSelection().getWatchedMovies(userId, limit);
            case NOT_SEEN -> Selection.getSelection().getNotWatchedMovies(userId, limit);
            case FAMOUS -> Selection.getSelection().getBestRated(limit);
        };
    }
}
