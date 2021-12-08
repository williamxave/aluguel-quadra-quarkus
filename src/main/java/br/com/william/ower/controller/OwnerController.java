package br.com.william.ower.controller;

import br.com.william.ower.dtos.OwnerDto;
import br.com.william.ower.service.OwnerService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/owner")
public class OwnerController {

    OwnerService ownerService;

    @Inject
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerController(@Valid OwnerDto ownerDto) {
        var uuid = ownerService.saveOwner(ownerDto);

        return Response.created(
                UriBuilder.fromResource(OwnerController.class)
                        .path(uuid)
                        .build())
                .build();
    }
}
