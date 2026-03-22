package model.repository;

import model.entity.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {

    public void addToWatchlist(int userId, int movieId) throws Exception {
        Connection conn = DBConnection.getConnection();

        String sql = "INSERT INTO watchlist(user_id, movie_id, watched) VALUES (?, ?, false)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);
        stmt.setInt(2, movieId);
        stmt.executeUpdate();
    }

    public void removeFromWatchlist(int userId, int movieId) throws Exception {
        Connection conn = DBConnection.getConnection();

        String sql = "DELETE FROM watchlist WHERE user_id=? AND movie_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);
        stmt.setInt(2, movieId);
        stmt.executeUpdate();
    }

    public void markAsWatched(int userId, int movieId) throws Exception {
        Connection conn = DBConnection.getConnection();

        String sql = "UPDATE watchlist SET watched=true WHERE user_id=? AND movie_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);
        stmt.setInt(2, movieId);
        stmt.executeUpdate();
    }

    public List<Movie> getUserWatchlist(int userId) throws Exception {
        List<Movie> movies = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql = """
            SELECT m.*, w.watched
            FROM movies m
            JOIN watchlist w ON m.id = w.movie_id
            WHERE w.user_id = ?
        """;

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            movies.add(new Movie(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getInt("year"),
                    rs.getBoolean("watched")
            ));
        }

        return movies;
    }
}