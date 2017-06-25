package com.mycompany.eeapp.data;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RepositoryProducer {

    @PersistenceContext
    private EntityManager entityManager;

    @Produces
    @Named("movieRepository")
    public JpaRepository<Movie,Long> exposeMovieRepository() {
        return new JpaRepository(Movie.class, entityManager);
    }

    @Produces
    @Named("directorRepository")
    public JpaRepository<Director,Long> exposeDirectorRepository() {
        return new JpaRepository(Director.class, entityManager);
    }
}
