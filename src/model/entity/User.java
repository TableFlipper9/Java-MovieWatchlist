package model.entity;

public class User {
    private int id;
    private String username;
    private String email = null;
    private String password = null;
    private String role;

    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public User(int id, String username, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
}