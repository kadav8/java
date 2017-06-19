package com.mycompany.eeapp.service;

import com.mycompany.eeapp.data.Movie;
import com.mycompany.eeapp.web.MetadataDto;
import com.mycompany.jpa.JpaRepository;

import javax.inject.Inject;
import javax.inject.Named;

public class MetadataService {

    @Inject
    @Named("movieRepository")
    private JpaRepository<Movie, Long> repo;

    public MetadataDto getMetadataById(final Long id) {
        //Movie movie = repo.findOne(id); // TODO
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("The Dark Knight");
        movie.setDirector("Christopher Nolan");
        movie.setYear(2008);
        MetadataDto dto = new MetadataDto();
        dto.setId(id);
        dto.setTitle(movie.getTitle());
        dto.setDirector(movie.getDirector());
        dto.setYear(movie.getYear());
        return dto;
    }
}
