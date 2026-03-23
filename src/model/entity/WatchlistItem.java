package model.entity;

public class WatchlistItem extends Movie {
    private boolean watched;

    public WatchlistItem(int id, String title, String genre, int year, boolean watched) {
        super(id, title, genre, year);
        this.watched = watched;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}