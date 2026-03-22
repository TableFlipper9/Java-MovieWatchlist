package model.entity;

public class Watchlist {
    private int userId;
    private int movieId;
    private boolean watched;

    public Watchlist(int userId, int movieId, boolean watched) {
        this.userId = userId;
        this.movieId = movieId;
        this.watched = watched;
    }

    // getters & setters
}