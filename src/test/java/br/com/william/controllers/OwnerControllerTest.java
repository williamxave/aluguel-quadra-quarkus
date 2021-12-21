package br.com.william.controllers;

import br.com.william.domain.owner.Owner;
import br.com.william.repositories.OwnerRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
class OwnerControllerTest {

    @Inject
    OwnerRepository ownerRepository;

    private Owner owner;

    @BeforeEach
    @Transactional
    void setUp() {
        createOwner();
    }

    @AfterEach
    @Transactional
    void tearDown() {
        ownerRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create owner")
    public void shouldCreateOwner(){
        var request = createJsonObject("teste@email.com");

      given()
              .contentType(MediaType.APPLICATION_JSON)
              .body(request.toString())
              .when()
              .post("/api/v1/owner/register")
              .then()
                        .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @DisplayName("Should not create owner if exists email")
    public void shouldNotCreateOwnerIfExistsEmail(){
        var request = createJsonObject("teste.te@email.com");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request.toString())
                .when()
                .post("/api/v1/owner/register")
                .then()
                        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .body("status", CoreMatchers.is(Response.Status.BAD_REQUEST.getStatusCode()))
                        .body("message", CoreMatchers.is("Validation error"));
    }

    @Test
    @DisplayName("Should Not Create Owner When any field is blank")
    public void shouldNotCreateOwnerWhenAnyFieldIsBlank(){
        var request = Json.createObjectBuilder()
                .add("name", "Rabbit")
                .add("password", "TabCdeF1$#")
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request.toString())
                .when()
                .post("/api/v1/owner/register")
                .then()
                        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .body("status", CoreMatchers.is(Response.Status.BAD_REQUEST.getStatusCode()))
                        .body("message", CoreMatchers.is("Validation error"));
    }

    @Test
    @DisplayName("Should find owner")
    public void shouldFindOwner(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("/api/v1/owner/" + owner.getExternalId())
                .then()
                        .statusCode(Response.Status.OK.getStatusCode())
                        .body("name", CoreMatchers.is("Pedro"))
                        .body("email", CoreMatchers.is("teste.te@email.com"));
    }

    @Test
    @DisplayName("Should not find owner")
    public void shouldNotFindOwner(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("/api/v1/owner/" + UUID.randomUUID())
                .then()
                        .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                        .body("status", CoreMatchers.is(Response.Status.NOT_FOUND.getStatusCode()))
                        .body("message", CoreMatchers.is("Object not found"));
    }

    @Test
    @DisplayName("Should update owner")
    public void shouldUpdateOwner(){
        var ownerUpdate =
                createJsonObject("testando@email.com");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ownerUpdate.toString())
                .when()
                .put("/api/v1/owner/" + owner.getExternalId())
                .then()
                        .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @DisplayName("Should not update owner")
    public void shouldNotUpdateOwner(){
        var ownerUpdate =
                createJsonObject("testando@email.com");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ownerUpdate.toString())
                .when()
                .put("/api/v1/owner/" + UUID.randomUUID())
                .then()
                        .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                        .body("status", CoreMatchers.is(Response.Status.NOT_FOUND.getStatusCode()))
                        .body("message", CoreMatchers.is("Object not found"));
    }

    @Test
    @DisplayName("Should delete owner")
    public void shouldDeleteOwner(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete("/api/v1/owner/" + owner.getExternalId())
                .then()
                        .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @DisplayName("Should not delete owner")
    public void shouldNotDeleteOwner(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete("/api/v1/owner/" + UUID.randomUUID())
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                .body("status", CoreMatchers.is(Response.Status.NOT_FOUND.getStatusCode()))
                .body("message", CoreMatchers.is("Object not found"));
    }

    private JsonObject createJsonObject(String email){
        return Json.createObjectBuilder()
                .add("name", "Rabbit")
                .add("email", email)
                .add("password", "TabCdeF1$#")
                .build();
    }

    private Owner createOwner(){
        owner = new Owner(
                "Pedro",
                "teste.te@email.com",
                "TabCdeF1$#"
        );
        ownerRepository.persist(owner);
        return owner;
    }
}