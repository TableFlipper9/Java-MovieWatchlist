package model.event;

import model.entity.Movie;

public class MovieEvent {

    private final EventType type;
    private final Movie movie;

    public MovieEvent(EventType type, Movie movie) {
        this.type = type;
        this.movie = movie;
    }

    public EventType getType() {
        return type;
    }

    public Movie getMovie() {
        return movie;
    }
}