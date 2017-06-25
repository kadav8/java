package com.mycompany.eeapp.ws;

import com.mycompany.eeapp.service.MetadataService;

import javax.inject.Inject;
import javax.jws.WebService;
import javax.validation.constraints.NotNull;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebService(serviceName = "MetadataService", portName = "MetadataPort")
public class MetadataEndpoint {

    @Inject
    private Logger logger;

    @Inject
    private MetadataService metadataService;

    public BaseResponse createMovie(@NotNull CreateMovieRequest createMovieRequest) {
        BaseResponse response = new BaseResponse();
        try {
            Long id = metadataService.createMovie(createMovieRequest);
            response.setResult("Movie created with id: " + id);
            response.setErrorCode(0);
            logger.info(response.getResult());
        } catch(Exception e) {
            response.setResult("Movie creating failed: " + e.getMessage());
            response.setErrorCode(1);
            logger.log(Level.SEVERE, response.getResult(), e);
        }
        return response;
    }

    public BaseResponse createDirector(@NotNull CreateDirectorRequest createDirectorRequest) {
        BaseResponse response = new BaseResponse();
        try {
            Long id = metadataService.createDirector(createDirectorRequest);
            response.setResult("Director created with id: " + id);
            response.setErrorCode(0);
            logger.info(response.getResult());
        } catch(Exception e) {
            response.setResult("Director creating failed: " + e.getMessage());
            response.setErrorCode(1);
            logger.log(Level.SEVERE, response.getResult(), e);
        }
        return response;
    }
}
