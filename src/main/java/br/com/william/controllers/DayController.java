package br.com.william.controllers;

import br.com.william.anotations.UniqueValue;
import br.com.william.domain.day.Day;
import br.com.william.domain.day.dtos.DayResponse;
import br.com.william.handlers.BusinessException;
import br.com.william.handlers.NotFoundException;
import br.com.william.services.DayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/api/v1/day")
public class DayController {
    private  Logger log = LoggerFactory.getLogger(DayController.class);

    DayService dayService;

    @Inject
    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findDay(@QueryParam("day") String date) {
        DayResponse dayResponse;
        try {
           dayResponse =
                    dayService.findDayResponse(date);
        }catch (NotFoundException e){
            log.error("Error in fetch day {}", date);
            throw e;
        }
        return Response.ok(dayResponse).build();
    }

    @POST
    public Response createDay(@QueryParam("day")  String date) {
        try {
            var day = dayService.validate(date);
            return Response.created(
                    UriBuilder.fromResource(DayController.class)
                            .path(day.getExternalId().toString())
                            .build())
                    .build();
        } catch (BusinessException e){
            log.error("Error creating a day {}", date);
            throw e;
        }
    }

    @PATCH
    @Path("/rent/{externalId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDay(@PathParam("externalId") String externalId) {
        try {
            dayService.updateDay(externalId);
        } catch (BusinessException e){
            log.error("Unable to update time");
            throw e;
        } catch (NotFoundException e){
            log.error("Hour not found hour: {} ", externalId);
            throw e;
        }
        return Response.noContent().build();
    }
}