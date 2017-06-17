package com.mycompany.eeapp.web;

import com.mycompany.Config;
import com.mycompany.eeapp.service.MetadataService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

@Path("meta")
public class MetadataResource {

    @Inject
    private Logger logger;

    @Context
    private UriInfo uriInfo;

    @Inject
    private MetadataService metadataService;

    @Inject @Config("mycompany.msg")
    private String msg;

    @GET
    @Path("/hello")
    public String hello() {
        logger.info("hello");
        return msg;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getMetadata(@PathParam("id") Long id) {
        logger.info("get metadata by id: " + id);
        return metadataService.getMetadataById(id);
    }

    @GET
    @Path("/getu/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getMetadataWithUrl(@PathParam("id") Long id) {
        logger.info("get metadata with url by id: " + id);
        MetadataDto dto = metadataService.getMetadataById(id);
        return Json.createObjectBuilder()
                .add("id", dto.getId())
                .add("title", dto.getTitle())
                .add("director", dto.getDirector())
                .add("year", dto.getYear())
                .add("_self", uriInfo.getBaseUriBuilder().path(MetadataResource.class).path(MetadataResource.class, "getMetadataWithUrl").build(dto.getId()).toString())
                .build();
    }
}
