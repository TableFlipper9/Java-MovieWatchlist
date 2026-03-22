package model.entity;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private int year;

    public Movie(int id, String title, String genre, int year) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
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