package model.repository;

import model.entity.User;

import java.sql.*;

public class UserRepository {

    public User findByUsername(String username) throws Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=?");
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
            );
        }
        return null;
    }
}