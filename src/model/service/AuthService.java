package model.service;

import model.entity.User;
import model.repository.UserRepository;

public class AuthService {
    private UserRepository repo = new UserRepository();

    public User login(String username, String password) throws Exception {
        User user = repo.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}