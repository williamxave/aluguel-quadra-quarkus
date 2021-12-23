package br.com.william.domain.owner.dtos;

import br.com.william.domain.hour.dtos.HourResponse;
import br.com.william.domain.owner.Owner;

import java.util.Set;
import java.util.stream.Collectors;

public class OwnerResponse {
    private String name;
    private String email;
    private Set<HourResponse> hourResponses;

    public OwnerResponse(Owner owner) {
        this.name = owner.getName();
        this.email = owner.getEmail();
        this.hourResponses = owner.getHours().stream().map(s ->
                         new HourResponse(s)).collect(Collectors.toSet());
    }

    @Deprecated
    public OwnerResponse(){
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<HourResponse> getHourResponses() {
        return hourResponses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
