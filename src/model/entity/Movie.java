package model.entity;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private int year;
    private boolean watched;

    public Movie(int id, String title, String genre, int year, boolean watched) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.watched = watched;
    }

    public int getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getGenre(){
        return genre;
    }

    public int getYear(){
        return year;
    }

    public boolean isWatched(){
        return watched;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public void setYear(int year){
        this.year = year;
    }
}