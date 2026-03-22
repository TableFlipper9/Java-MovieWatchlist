package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.entity.User;
import model.service.AuthService;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private AuthService authService = new AuthService();

    @FXML
    public void handleLogin() throws Exception {

        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields").showAndWait();
            return;
        }

        User user = authService.login(
                usernameField.getText(),
                passwordField.getText()
        );

        if (user != null) {
            switchMain(user);

        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password").showAndWait();
        }
    }

    @FXML
    public void handleVisitor() throws Exception {
        User visitor = new User(0, "guest", "", "VISITOR");

        switchMain(visitor);
    }

    private void switchMain(User visitor) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        Parent root = loader.load();

        MovieController controller = loader.getController();
        controller.setUser(visitor);

        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}