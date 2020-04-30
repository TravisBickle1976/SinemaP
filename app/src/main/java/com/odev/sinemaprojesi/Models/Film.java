package com.odev.sinemaprojesi.Models;

import java.util.List;

public class Film {

    public String filmImage_id;
    public String film_name;
    public String director_name;
    public String writer_name;
    public String releaseDate;
    List<MoviePlayer> movie_cast;
    public String genre;
    String film_id;
    int price;

    public Film(String film_id, String filmImage_id, String film_name, String director_name, String writer_name, String releaseDate, List<MoviePlayer> movie_cast, String genre
    , int price) {
        this.film_id = film_id;
        this.filmImage_id = filmImage_id;
        this.film_name = film_name;
        this.director_name = director_name;
        this.writer_name = writer_name;
        this.releaseDate = releaseDate;
        this.movie_cast = movie_cast;
        this.genre = genre;
        this.price = price;
    }

    public Film(){

    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFilm_id() {
        return film_id;
    }

    public void setFilm_id(String film_id) {
        this.film_id = film_id;
    }

    public String getFilmImage_id() {
        return filmImage_id;
    }

    public void setFilmImage_id(String filmImage_id) {
        this.filmImage_id = filmImage_id;
    }

    public String getFilm_name() {
        return film_name;
    }

    public void setFilm_name(String film_name) {
        this.film_name = film_name;
    }

    public String getDirector_name() {
        return director_name;
    }

    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }

    public String getWriter_name() {
        return writer_name;
    }

    public void setWriter_name(String writer_name) {
        this.writer_name = writer_name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<MoviePlayer> getMovie_cast() {
        return movie_cast;
    }

    public void setMovie_cast(List<MoviePlayer> movie_cast) {
        this.movie_cast = movie_cast;
    }
}
