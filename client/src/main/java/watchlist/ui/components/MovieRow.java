package watchlist.ui.components;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import watchlist.enums.ListCategory;
import watchlist.forServer.models.MovieInfos;
import watchlist.forServer.serverConn.Selection;

import java.util.List;

public class MovieRow extends VBox {
    private ListCategory listCategory;
    private int limit;

    //region constructor, getter & setter
    public MovieRow(ListCategory listCategory, int limit) {
        this.listCategory = listCategory;
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
    //endregion

    public void initBox() {
        Text titel = new Text(listCategory.getTitel());
        this.getChildren().addAll(titel, getContent());
    }

    public ScrollPane getContent() {
        HBox h = new HBox();
        List<MovieInfos> movieData = getMovieData();

        for (MovieInfos m : movieData) {
            Image image = new Image(m.getPosterUrl());
            h.getChildren().add(new ImageView(image));
        }

        ScrollPane scrollPane = new ScrollPane(h);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scrollPane;
    }

    public List<MovieInfos> getMovieData() {
        return switch (listCategory) {
            case SHORTLY_SAVED -> Selection.getINSTANCE().getShortlyAdded(limit);
            case MY_WATCHLIST -> Selection.getINSTANCE().getWatchlist();
            case SEEN -> Selection.getINSTANCE().getWatchedMovies(limit);
            case NOT_SEEN -> Selection.getINSTANCE().getNotWatchedMovies(limit);
            case FAMOUS -> Selection.getINSTANCE().getBestRated(limit);
        };
    }
}
