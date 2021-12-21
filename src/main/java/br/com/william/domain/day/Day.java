package br.com.william.domain.day;

import br.com.william.domain.hour.Hour;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID externalId = UUID.randomUUID();

    private String day;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "day")
    private List<Hour> hours = new ArrayList<>();

    public Day(String day) {
        this.day = day;
    }

    @Deprecated
    public Day(){
    }

    public Boolean checkRentHour(){
        return getHours().stream().filter( s ->
                s.getChecKRent()).count() == hours.stream().count();
    }

    public UUID getExternalId() {
        return externalId;
    }

    public String getDay() {
        return day;
    }

    public List<Hour> getHours() {
        return Collections.unmodifiableList(this.hours);
    }

    public void addHours(List<Hour> hours){
        this.hours.addAll(hours);
    }
}