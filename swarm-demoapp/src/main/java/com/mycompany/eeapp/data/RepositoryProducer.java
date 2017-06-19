package com.mycompany.eeapp.data;

import com.mycompany.jpa.JpaRepository;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RepositoryProducer {

    @PersistenceContext(unitName="pu")
    private EntityManager em; // TODO

    @Produces
    @Named("movieRepository")
    public JpaRepository<Movie,Long> exposeMovieRepository() {
        return new JpaRepository(Movie.class, em);
    }
}
