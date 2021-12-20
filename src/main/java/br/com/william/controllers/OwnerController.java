package br.com.william.controllers;

import br.com.william.dtos.OwnerDto;

import br.com.william.handlers.BadRequestExceptionCustom;
import br.com.william.services.OwnerService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.UUID;

@Path("/api/v1/owner")
public class OwnerController {

    OwnerService ownerService;

    @Inject
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerController(OwnerDto ownerDto) throws BadRequestExceptionCustom{
        ownerService.validate(ownerDto);
        var uuid = ownerService.saveOwner(ownerDto);
        return Response.created(
                UriBuilder.fromResource(OwnerController.class)
                        .path(uuid)
                        .build())
                .build();
    }

    @GET
    @Path("/{externalId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findOwner(@PathParam("externalId") String externalId) {
        var ownerResponse = ownerService.findOwnerResponse(UUID.fromString(externalId));
        return Response.ok(ownerResponse).build();
    }

    @DELETE
    @Path("/{externalId}")
    public Response deleteOwner(@PathParam("externalId") String externalId) {
        ownerService.deleteOwner(UUID.fromString(externalId));
        return Response.ok().build();
    }

    @PUT
    @Path("/{externalId}")
    public Response updateOwner(@PathParam("externalId") String externalId, OwnerDto ownerDto) {
        ownerService.validate(ownerDto);
        ownerService.updateOwner(UUID.fromString(externalId), ownerDto);
        return Response.ok().build();
    }
}
