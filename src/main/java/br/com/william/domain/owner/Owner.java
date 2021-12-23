package br.com.william.domain.owner;

import io.quarkus.elytron.security.common.BcryptUtil;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
public class Owner{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID externalId = UUID.randomUUID();

    @NotBlank
    private String name;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    @Deprecated
    public Owner() {
    }

    public Owner(String name,
                 String email,
                 String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = BcryptUtil.bcryptHash(password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public Long getId() {
        return id;
    }
}


