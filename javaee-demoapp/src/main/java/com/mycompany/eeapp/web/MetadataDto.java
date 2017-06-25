package com.mycompany.eeapp.web;

import java.util.List;

public class MetadataDto {

    private Long movieId;
    private String title;
    private Integer year;
    private Long directorId;
    private String directorName;
    private List<String> directorOtherMovies;
    private String _self;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public List<String> getDirectorOtherMovies() {
        return directorOtherMovies;
    }

    public void setDirectorOtherMovies(List<String> directorOtherMovies) {
        this.directorOtherMovies = directorOtherMovies;
    }

    public String get_self() {
        return _self;
    }

    public void set_self(String _self) {
        this._self = _self;
    }
}
