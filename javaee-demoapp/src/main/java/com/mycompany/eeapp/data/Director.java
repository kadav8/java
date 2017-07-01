package com.mycompany.eeapp.data;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "director.findOneByName", query = "select d from Director d where d.name = :name")})
public class Director extends AbstractEntity {

    private String name;
    @OneToMany
    private List<Movie> movies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
