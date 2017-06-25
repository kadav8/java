package com.mycompany.eeapp.data;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(
        {
            @NamedQuery(name = "director.findOneByName", query = "select d from Director d where d.name = :name"),
        })
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany
    private List<Movie> movies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
