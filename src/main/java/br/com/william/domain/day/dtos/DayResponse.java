package br.com.william.domain.day.dtos;

import br.com.william.domain.day.Day;
import br.com.william.domain.hour.dtos.HourResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DayResponse {

    private UUID externalId;

    private String day;

    private List<HourResponse> hours;

    public DayResponse(Day day) {
        this.externalId = day.getExternalId();
        this.day = day.getDay();
        this.hours =
               day.getHours().stream().filter(s ->
                       !s.getChecKRent()).map( hour ->
                            new HourResponse(hour)).collect(Collectors.toList());
    }

    public String getDay() {
        return day;
    }

    public List<HourResponse> getHours() {
        return hours;
    }

    public UUID getExternalId() {
        return externalId;
    }
}
