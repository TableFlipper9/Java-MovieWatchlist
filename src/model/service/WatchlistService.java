package model.service;

import model.entity.Movie;
import model.repository.WatchlistRepository;

import java.util.List;

public class WatchlistService {

    private WatchlistRepository repo = new WatchlistRepository();

    public void addMovie(int userId, int movieId, String role) throws Exception {
        if (role.equals("VISITOR")) {
            throw new RuntimeException("Visitors cannot modify watchlist");
        }
        repo.addToWatchlist(userId, movieId);
    }

    public void removeMovie(int userId, int movieId, String role) throws Exception {
        if (role.equals("VISITOR")) {
            throw new RuntimeException("Visitors cannot modify watchlist");
        }
        repo.removeFromWatchlist(userId, movieId);
    }

    public void markWatched(int userId, int movieId, String role) throws Exception {
        if (role.equals("VISITOR")) {
            throw new RuntimeException("Visitors cannot modify watchlist");
        }
        repo.markAsWatched(userId, movieId);
    }

    public List<Movie> getWatchlist(int userId) throws Exception {
        return repo.getUserWatchlist(userId);
    }
}