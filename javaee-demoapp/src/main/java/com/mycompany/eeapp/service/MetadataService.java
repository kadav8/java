package com.mycompany.eeapp.service;

import com.mycompany.eeapp.LatencyLogger;
import com.mycompany.eeapp.data.Director;
import com.mycompany.eeapp.data.JpaRepository;
import com.mycompany.eeapp.data.Movie;
import com.mycompany.eeapp.web.MetadataDto;
import com.mycompany.eeapp.ws.CreateDirectorRequest;
import com.mycompany.eeapp.ws.CreateMovieRequest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MetadataService {

	@Inject private Logger logger;

    @Inject
    @Named("movieRepository")
    private JpaRepository<Movie, Long> movieRepo;

    @Inject
    @Named("directorRepository")
    private JpaRepository<Director, Long> directorRepo;

    @LatencyLogger
    @Transactional
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
	    logger.log(Level.INFO, "{movie} persisted", movie);
	    return movie.getId();
    }

    @LatencyLogger
    @Transactional
    public Long createDirector(final CreateDirectorRequest createDirectorRequest) {
        Director director = new Director();
        director.setName(createDirectorRequest.getName());
        director = directorRepo.create(director);
	    logger.log(Level.INFO, "{director} persisted", director);
        return director.getId();
    }

    public MetadataDto getMetadata(final Long id) {
	    logger.log(Level.INFO, "get metadata [" + this.hashCode() + "]");

        MetadataDto dto = null;
        Movie movie = movieRepo.findOne(id);
        if (movie != null) {
            dto = new MetadataDto();
            dto.setMovieId(id);
            dto.setTitle(movie.getTitle());
            dto.setYear(movie.getYear());

            Director director = movie.getDirector();
            if (director != null) {
                dto.setDirectorId(director.getId());
                dto.setDirectorName(director.getName());
                if (director.getMovies() != null) {
                    dto.setDirectorOtherMovies(
                            director.getMovies()
                                    .stream()
                                    .filter(m -> !m.getTitle().equalsIgnoreCase(movie.getTitle()))
                                    .map(m -> m.getTitle())
		                            .collect(Collectors.toList()));
                }
            }
        }
        return dto;
    }
}
