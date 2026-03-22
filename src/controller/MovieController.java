package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
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

    // Movie table columns
    @FXML private TableColumn<Movie, String> titleCol;
    @FXML private TableColumn<Movie, String> genreCol;
    @FXML private TableColumn<Movie, Integer> yearCol;

    // Watchlist columns
    @FXML private TableColumn<Movie, String> watchTitleCol;
    @FXML private TableColumn<Movie, String> watchGenreCol;
    @FXML private TableColumn<Movie, Integer> watchYearCol;
    @FXML private TableColumn<Movie, Boolean> watchedCol;

    @FXML private Button addBtn;
    @FXML private Button updateBtn;
    @FXML private Button deleteBtn;
    @FXML private Button addToWatchlistBtn;

    private final MovieService service = new MovieService();
    private final WatchlistService watchlistService = new WatchlistService();

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        applyRolePermissions();
        loadMovies();
        loadWatchlist();
    }

    @FXML
    public void initialize() {

        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        genreCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenre()));
        yearCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getYear()));

        watchTitleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        watchGenreCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenre()));
        watchYearCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getYear()));

        HandleHighlight(movieTable);

        HandleHighlight(watchlistTable);

        movieTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, movie) -> {
            if (movie != null) {
                titleField.setText(movie.getTitle());
                genreField.setText(movie.getGenre());
                yearField.setText(String.valueOf(movie.getYear()));
                updateWatchlistButtonState(movie);
            }
        });
    }

    private void HandleHighlight(TableView<Movie> movieTable) {
        movieTable.setRowFactory(tv -> {
            TableRow<Movie> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    row.setStyle("-fx-background-color: #cce5ff;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });
    }

    private void applyRolePermissions() {

        if (currentUser == null) return;

        String role = currentUser.getRole();

        switch (role) {

            case "VISITOR":
                hide(addBtn);
                hide(updateBtn);
                hide(deleteBtn);
                hide(addToWatchlistBtn);
                watchlistTable.setVisible(false);
                watchlistTable.setManaged(false);
                break;

            case "USER":
                hide(addBtn);
                hide(updateBtn);
                hide(deleteBtn);
                break;

            case "ADMIN":
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
            if (currentUser == null) return;

            if (!currentUser.getRole().equals("VISITOR")) {
                watchlistTable.getItems().setAll(
                        watchlistService.getWatchlist(currentUser.getId())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateWatchlistButtonState(Movie movie) {
        try {
            if (currentUser == null || currentUser.getRole().equals("VISITOR")) {
                addToWatchlistBtn.setDisable(true);
                return;
            }

            List<Movie> watchlist = watchlistService.getWatchlist(currentUser.getId());

            boolean exists = watchlist.stream()
                    .anyMatch(m -> m.getId() == movie.getId());

            addToWatchlistBtn.setDisable(exists);

        } catch (Exception e) {
            addToWatchlistBtn.setDisable(true);
        }
    }

    @FXML
    public void addMovie() {
        try {
            Movie movie = new Movie(
                    0,
                    titleField.getText(),
                    genreField.getText(),
                    Integer.parseInt(yearField.getText())
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
            movieTable.getItems().setAll(
                    service.search(searchField.getText())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void addToWatchlist() {
        try {
            if (currentUser.getRole().equals("VISITOR")) {
                showError("Visitors cannot use watchlist");
                return;
            }

            Movie selected = movieTable.getSelectionModel().getSelectedItem();
            if (selected == null) return;

            watchlistService.addMovie(
                    currentUser.getId(),
                    selected.getId(),
                    currentUser.getRole()
            );

            loadWatchlist();
            updateWatchlistButtonState(selected);

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void removeFromWatchlist() {
        try {
            Movie selected = watchlistTable.getSelectionModel().getSelectedItem();
            if (selected == null) return;

            watchlistService.removeMovie(
                    currentUser.getId(),
                    selected.getId(),
                    currentUser.getRole()
            );

            loadWatchlist();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void markWatched() {
        try {
            Movie selected = watchlistTable.getSelectionModel().getSelectedItem();
            if (selected == null) return;

            watchlistService.markWatched(
                    currentUser.getId(),
                    selected.getId(),
                    currentUser.getRole()
            );

            loadWatchlist();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }
}