package br.com.william.controllers;

import br.com.william.domain.hour.dtos.HourResponse;
import br.com.william.services.DayService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/api/v1/day")
public class DayController {

    DayService dayService;

    @Inject
    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @GET
    public Response findDay(@QueryParam("day") String date){
        var possibleDay = dayService.finday(date);

        if(possibleDay.isPresent()){
            var dayResponse =
                    dayService.findDayResponse(possibleDay);
            return Response.ok(possibleDay).build();
        }
        var createdDay =
                dayService.createDayToReponse(date);
        return Response.ok(createdDay).build();
    }

    @PATCH
    @Path("/rent/{externalId}")
    public Response updateDay(@PathParam("externalId") String externalId){
        dayService.updateDay(externalId);
        return Response.noContent().build();
    }
}