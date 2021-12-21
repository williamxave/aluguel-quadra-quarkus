package br.com.william.domain.hour;

import br.com.william.domain.day.Day;
import br.com.william.enums.PossibleHour;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Hour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID externalId = UUID.randomUUID();

    private Boolean checKRent;

    @ManyToOne
    @JoinColumn(name = "day_id")
    private Day day;

    @Enumerated(EnumType.STRING)
    private PossibleHour possibleHour;

    public Hour(Boolean checKRent,
                PossibleHour possibleHour) {
        this.checKRent = checKRent;
        this.possibleHour = possibleHour;
    }

    @Deprecated
    public Hour(){}

    public Boolean getChecKRent() {
        return checKRent;
    }

    public PossibleHour getPossibleHour() {
        return possibleHour;
    }

    public void updateRentHour(){
        this.checKRent = true;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}

