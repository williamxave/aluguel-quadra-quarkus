package br.com.william.domain.hour.dtos;

import br.com.william.domain.hour.Hour;
import br.com.william.enums.PossibleHour;

import java.time.LocalDateTime;
import java.util.UUID;

public class HourResponse {

    private UUID externalId;

    private Boolean checKRent;

    private PossibleHour possibleHour;

    private LocalDateTime rented;

    public HourResponse(Hour hour) {
        this.checKRent = hour.getChecKRent();
        this.possibleHour = hour.getPossibleHour();
        this.externalId = hour.getExternalId();
        this.rented = hour.getRented();
    }

    public UUID getExternalId() {
        return externalId;
    }

    public PossibleHour getPossibleHour() {
        return possibleHour;
    }

    public Boolean getChecKRent() {
        return checKRent;
    }

    public LocalDateTime getRented() {
        return rented;
    }
}
