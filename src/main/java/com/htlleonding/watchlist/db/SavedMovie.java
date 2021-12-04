package com.htlleonding.watchlist.db;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class SavedMovie {
    @EmbeddedId
    private MovieId movieId;
}
