package br.com.william.dtos;

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

    public OwnerDto(String name,
                    String email,
                    String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
