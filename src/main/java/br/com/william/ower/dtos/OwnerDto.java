package br.com.william.ower.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class OwnerDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @Deprecated
    public OwnerDto() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
