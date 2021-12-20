package br.com.william.dtos;

import br.com.william.domain.Owner;

public class OwnerResponse {
    private String name;
    private String email;

    public OwnerResponse(Owner owner) {
        this.name = owner.getName();
        this.email = owner.getEmail();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
