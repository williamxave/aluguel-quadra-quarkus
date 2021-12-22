package br.com.william.domain.hour.dtos;

import br.com.william.domain.hour.Hour;
import br.com.william.enums.PossibleHour;

import java.util.UUID;

public class HourResponse {

    private UUID externalId;

    private Boolean checKRent;

    private PossibleHour possibleHour;

    public HourResponse(Hour hour) {
        this.checKRent = hour.getChecKRent();
        this.possibleHour = hour.getPossibleHour();
        this.externalId = hour.getExternalId();
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
}
