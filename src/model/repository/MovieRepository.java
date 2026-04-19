package model.repository;

import model.entity.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    public Movie findById(int id) throws Exception {
        String sql = "SELECT * FROM movies WHERE id = ?";

        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getInt("year")
                );
            }
        }

        return null;
    }

    public void add(Movie movie) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO movies(title, genre, year) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, movie.getTitle());
        stmt.setString(2, movie.getGenre());
        stmt.setInt(3, movie.getYear());
        stmt.executeUpdate();
    }

    public List<Movie> findAll() throws Exception {
        List<Movie> movies = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM movies");

        while (rs.next()) {
            movies.add(new Movie(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getInt("year")
            ));
        }
        return movies;
    }

    public void delete(int id) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM movies WHERE id=?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public void update(Movie movie) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();

        String sql = "UPDATE movies SET title=?, genre=?, year=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, movie.getTitle());
        stmt.setString(2, movie.getGenre());
        stmt.setInt(3, movie.getYear());
        stmt.setInt(4, movie.getId());

        stmt.executeUpdate();
    }
}