package br.com.william.domain.owner;

import br.com.william.domain.hour.Hour;
import br.com.william.utils.PasswordEncrypt;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Owner {

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

    @OneToMany(cascade = CascadeType.ALL)
    Set<Hour> hours = new HashSet<>();

    private LocalDateTime lastLogin;

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
        this.password = password;
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

    public Set<Hour> getHours() {
        return hours;
    }

    public void addHours(Hour hour) {
        this.hours.add(hour);
    }

    public void setLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    public String getPassword() {
        return password;
    }

    public String encryptPassword(String password){
      return this.password =
                PasswordEncrypt.encryptPassword(password);
    }
}