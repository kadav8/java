package com.mycompany.eeapp.web;

import com.mycompany.eeapp.service.MetadataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("meta")
public class MetadataResource {

    @Inject
    private MetadataService metadataService;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MetadataDto getMetadata(@PathParam("id") Long id) {
        return metadataService.getMetadataById(id);
    }
}
