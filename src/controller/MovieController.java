package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.entity.Movie;
import model.entity.User;
import model.service.MovieService;
import model.service.WatchlistService;

import java.util.List;

public class MovieController {

    @FXML private TextField titleField;
    @FXML private TextField genreField;
    @FXML private TextField yearField;
    @FXML private TextField searchField;
    @FXML private TextField genreFilterField;

    @FXML private TableView<Movie> movieTable;
    @FXML private TableView<Movie> watchlistTable;
    @FXML private TableColumn<Movie, String> titleCol;
    @FXML private TableColumn<Movie, String> genreCol;
    @FXML private TableColumn<Movie, Integer> yearCol;
    @FXML private TableColumn<Movie, Boolean> watchedCol;

    @FXML private Button addBtn;
    @FXML private Button updateBtn;
    @FXML private Button deleteBtn;

    private MovieService service = new MovieService();
    private WatchlistService watchlistService = new WatchlistService();

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        applyRolePermissions();
    }

    @FXML
    public void initialize() {
        titleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        genreCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getGenre()));
        yearCol.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getYear()));
        watchedCol.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().isWatched()));

        loadMovies();

        movieTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, movie) -> {
            if (movie != null) {
                titleField.setText(movie.getTitle());
                genreField.setText(movie.getGenre());
                yearField.setText(String.valueOf(movie.getYear()));
            }
        });
    }

    private void applyRolePermissions() {
        String role = currentUser.getRole();

        switch (role) {
            case "VISITOR":
                hide(addBtn);
                hide(updateBtn);
                hide(deleteBtn);
                break;

            case "USER":
                hide(deleteBtn); // user can't delete
                break;

            case "ADMIN":
                // full access → nothing hidden
                break;
        }
    }

    private void hide(Button btn) {
        btn.setVisible(false);
        btn.setManaged(false);
    }

    private void loadMovies() {
        try {
            movieTable.getItems().setAll(service.getAllMovies());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadWatchlist() {
        try {
            if (!currentUser.getRole().equals("VISITOR")) {
                watchlistTable.getItems().setAll(
                        watchlistService.getWatchlist(currentUser.getId())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addMovie() {
        try {
            Movie movie = new Movie(
                    0,
                    titleField.getText(),
                    genreField.getText(),
                    Integer.parseInt(yearField.getText()),
                    false
            );

            service.addMovie(movie, currentUser.getRole());
            loadMovies();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void updateMovie() {
        try {
            Movie selected = movieTable.getSelectionModel().getSelectedItem();

            if (selected == null) return;

            selected.setTitle(titleField.getText());
            selected.setGenre(genreField.getText());
            selected.setYear(Integer.parseInt(yearField.getText()));

            service.updateMovie(selected, currentUser.getRole());
            loadMovies();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void deleteMovie() {
        try {
            Movie selected = movieTable.getSelectionModel().getSelectedItem();

            if (selected == null) return;

            service.deleteMovie(selected.getId(), currentUser.getRole());
            loadMovies();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void searchMovie() {
        try {
            List<Movie> result = service.search(searchField.getText());
            movieTable.getItems().setAll(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    public void sortByTitle() {
        movieTable.getItems().sort(
                java.util.Comparator.comparing(Movie::getTitle)
        );
    }

    @FXML
    public void sortAsc() {
        try {
            movieTable.getItems().setAll(service.sortByTitle(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sortDesc() {
        try {
            movieTable.getItems().setAll(service.sortByTitle(false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void filterByGenre() {
        try {
            movieTable.getItems().setAll(
                    service.filterByGenre(genreFilterField.getText())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addToWatchlist() throws Exception {
        Movie selected = movieTable.getSelectionModel().getSelectedItem();
        watchlistService.addMovie(currentUser.getId(), selected.getId(), currentUser.getRole());
    }

    @FXML
    public void removeFromWatchlist() throws Exception {
        Movie selected = watchlistTable.getSelectionModel().getSelectedItem();
        watchlistService.removeMovie(currentUser.getId(), selected.getId(), currentUser.getRole());
    }

    @FXML
    public void markWatched() throws Exception {
        Movie selected = watchlistTable.getSelectionModel().getSelectedItem();
        watchlistService.markWatched(currentUser.getId(), selected.getId(), currentUser.getRole());
    }
}