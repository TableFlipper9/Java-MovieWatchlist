package model.service;

import model.entity.Movie;
import model.repository.MovieRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {
    private MovieRepository repo = new MovieRepository();

    public void addMovie(Movie movie, String role) throws Exception {
        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Access denied");
        }
        repo.add(movie);
    }

    public List<Movie> getAllMovies() throws Exception {
        return repo.findAll();
    }

    public List<Movie> search(String keyword) throws Exception {
        return repo.findAll().stream()
                .filter(m -> m.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void updateMovie(Movie movie, String role) throws Exception {
        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Access denied");
        }
        repo.update(movie);
    }

    public void deleteMovie(int id, String role) throws Exception {
        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Only admin can delete");
        }
        repo.delete(id);
    }

    public List<Movie> filter(List<Movie> movies, String genre) {
        return movies.stream()
                .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                .toList();
    }

    public List<Movie> sortAsc(List<Movie> movies) {
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getTitle))
                .toList();
    }

    public List<Movie> sortDesc(List<Movie> movies) {
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getTitle).reversed())
                .toList();
    }
}