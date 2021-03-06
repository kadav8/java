package com.mycompany.eeapp.web;

import com.mycompany.eeapp.Config;
import com.mycompany.eeapp.service.AsynchService;
import com.mycompany.eeapp.service.MetadataService;
import com.mycompany.eeapp.ws.CreateDirectorRequest;
import com.mycompany.eeapp.ws.CreateMovieRequest;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("meta")
public class MetadataResource {

    @Inject private Logger logger;
    @Inject private MetadataService metadataService;
    @Inject private AsynchService asynchService;
	@Context private UriInfo uriInfo;
    @Inject @Config("mycompany.msg") private String msg;

    @GET
    @Path("/hello")
    public String hello() {
        return msg;
    }

    @GET
    @Path("/asynch")
    public String asynch() {
		return asynchService.startService();
    }

    @POST
    @Path("/create/movie")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postMovie(@NotNull CreateMovieRequest createMovieRequest) {
        Long id = metadataService.createMovie(createMovieRequest);
        logger.info("Movie created with id: " + id);
    }

    @POST
    @Path("/create/director")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postMovie(@NotNull CreateDirectorRequest createDirectorRequest) {
        Long id = metadataService.createDirector(createDirectorRequest);
        logger.log(Level.INFO, "Director created with id: {0}", id);
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getMetadata(@PathParam("id") Long id) {
        logger.info("Get metadata by id: " + id);
        MetadataDto dto = metadataService.getMetadata(id);
        if(dto != null) {
	        dto.set_self(uriInfo.getBaseUriBuilder()
			        .path(MetadataResource.class)
			        .path(MetadataResource.class, "getMetadata")
			        .build(dto.getMovieId()).toString());
        }
        return dto;
    }

    @GET
    @Path("/getsimple/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getSimpleMetadata(@PathParam("id") Long id) {
        logger.info("Get metadata with url by id: " + id);
        MetadataDto dto = metadataService.getMetadata(id);
        if(dto != null) {
	        return Json.createObjectBuilder()
			        .add("title", dto.getTitle())
			        .add("year", dto.getYear())
			        .build();
        }
        return null;
    }
}
