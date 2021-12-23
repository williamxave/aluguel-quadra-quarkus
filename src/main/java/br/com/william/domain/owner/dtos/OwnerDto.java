package br.com.william.domain.owner.dtos;

import br.com.william.anotations.Password;
import br.com.william.anotations.UniqueValue;
import br.com.william.domain.owner.Owner;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class OwnerDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    @UniqueValue(fieldName = "email", domainClass = Owner.class)
    private String email;

    @NotBlank
    @Password(fieldName = "password", domainClass = Owner.class)
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