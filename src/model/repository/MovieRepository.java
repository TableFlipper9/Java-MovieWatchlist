package model.repository;

import model.entity.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    public void add(Movie movie) throws Exception {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO movies(title, genre, year, watched) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, movie.getTitle());
        stmt.setString(2, movie.getGenre());
        stmt.setInt(3, movie.getYear());
        stmt.setBoolean(4, movie.isWatched());
        stmt.executeUpdate();
    }

    public List<Movie> findAll() throws Exception {
        List<Movie> movies = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM movies");

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

    public void delete(int id) throws Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM movies WHERE id=?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public void update(Movie movie) throws Exception {
        Connection conn = DBConnection.getConnection();

        String sql = "UPDATE movies SET title=?, genre=?, year=?, watched=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, movie.getTitle());
        stmt.setString(2, movie.getGenre());
        stmt.setInt(3, movie.getYear());
        stmt.setBoolean(4, movie.isWatched());
        stmt.setInt(5, movie.getId());

        stmt.executeUpdate();
    }

    public List<Movie> searchByTitle(String keyword) throws Exception {
        List<Movie> movies = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM movies WHERE LOWER(title) LIKE ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + keyword.toLowerCase() + "%");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            movies.add(map(rs));
        }

        return movies;
    }

    public List<Movie> filterByGenre(String genre) throws Exception {
        List<Movie> movies = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM movies WHERE genre = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, genre);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            movies.add(map(rs));
        }

        return movies;
    }

    public List<Movie> sortByTitle(boolean ascending) throws Exception {
        List<Movie> movies = new ArrayList<>();

        String order = ascending ? "ASC" : "DESC";
        String sql = "SELECT * FROM movies ORDER BY title " + order;

        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery(sql);

        while (rs.next()) {
            movies.add(map(rs));
        }

        return movies;
    }

    // helper
    private Movie map(ResultSet rs) throws Exception {
        return new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getInt("year"),
                rs.getBoolean("watched")
        );
    }
}