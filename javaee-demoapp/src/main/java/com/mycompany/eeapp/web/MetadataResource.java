package com.mycompany.eeapp.web;

import com.mycompany.eeapp.Config;
import com.mycompany.eeapp.service.MetadataService;
import com.mycompany.eeapp.ws.CreateDirectorRequest;
import com.mycompany.eeapp.ws.CreateMovieRequest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

@Path("meta")
@Stateless
public class MetadataResource {

    @Inject
    private Logger logger;

    @Inject
    private MetadataService metadataService;

    @Inject @Config(key = "mycompany.msg")
    private String msg;
    @Inject @Config(key = "mycompany.msg2", defaultValue = "Java EE")
    private String msg2;

    @GET
    @Path("/hello")
    public String hello() {
        return msg + " " + msg2;
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
        logger.info("Director created with id: " + id);
    }

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getMetadata(@PathParam("id") Long id) {
        logger.info("get metadata by id: " + id);
        MetadataDto dto = metadataService.getMetadata(id);
        dto.set_self(uriInfo.getBaseUriBuilder()
                .path(MetadataResource.class)
                .path(MetadataResource.class, "getMetadata")
                .build(dto.getMovieId()).toString());
        return dto;
    }

    @GET
    @Path("/getsimple/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getSimpleMetadata(@PathParam("id") Long id) {
        logger.info("get metadata with url by id: " + id);
        MetadataDto dto = metadataService.getMetadata(id);
        return Json.createObjectBuilder()
                .add("title", dto.getTitle())
                .add("year", dto.getYear())
                .build();
    }
}
