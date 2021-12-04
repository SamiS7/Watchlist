package com.htlleonding.watchlist.UI.components;

import com.htlleonding.watchlist.db.MovieInfos;
import com.htlleonding.watchlist.enums.ListCategory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.LinkedList;

public class MovieRow extends VBox {
    ListCategory listCategory;

    public MovieRow(ListCategory listCategory) {
        this.listCategory = listCategory;
    }

    public void initBox() {
        Text titel = new Text(listCategory.getTitel());

    }

    public HBox getContent() {
        HBox h = new HBox();
        LinkedList<MovieInfos> movieInfos = getMovieData();


    }

    public LinkedList<MovieInfos> getMovieData() {

    }


}
