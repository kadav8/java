package com.mycompany.eeapp.service;

import com.mycompany.eeapp.data.Director;
import com.mycompany.eeapp.data.JpaRepository;
import com.mycompany.eeapp.data.Movie;
import com.mycompany.eeapp.web.MetadataDto;
import com.mycompany.eeapp.ws.CreateDirectorRequest;
import com.mycompany.eeapp.ws.CreateMovieRequest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.stream.Collectors;

@Stateless
public class MetadataService {

    @Inject @Named("movieRepository")
    private JpaRepository<Movie, Long> movieRepo;

    @Inject @Named("directorRepository")
    private JpaRepository<Director, Long> directorRepo;

    public Long createMovie(final CreateMovieRequest createMovieRequest) {
        Movie movie = new Movie();
        movie.setTitle(createMovieRequest.getTitle());
        Director director = directorRepo.findOneByNamedQuery(
                "director.findOneByName", "name", createMovieRequest.getDirectorName());
        if(director != null) {
            director.getMovies().add(movie);
            movie.setDirector(director);
        }
        movie.setYear(createMovieRequest.getYear());
        movie = movieRepo.create(movie);
        return movie.getId();
    }

    public Long createDirector(final CreateDirectorRequest createDirectorRequest) {
        Director director = new Director();
        director.setName(createDirectorRequest.getName());
        director = directorRepo.create(director);
        return director.getId();
    }

    public MetadataDto getMetadata(final Long id) {
        MetadataDto dto = null;
        Movie movie = movieRepo.findOne(id);
        if (movie != null) {
            dto = new MetadataDto();
            dto.setMovieId(id);
            dto.setTitle(movie.getTitle());
            dto.setYear(movie.getYear());

            Director director = movie.getDirector();
            if (director != null) {
                dto.setDirectorId(movie.getDirector().getId());
                dto.setDirectorName(movie.getDirector().getName());
                if (director.getMovies() != null) {
                    dto.setDirectorOtherMovies(
                            director.getMovies()
                                    .stream()
                                    .filter(m -> !m.getTitle().equalsIgnoreCase(movie.getTitle()))
                                    .map(m -> m.getTitle()).collect(Collectors.toList()));
                }
            }
        }
        return dto;
    }
}
