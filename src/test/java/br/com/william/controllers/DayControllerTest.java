package br.com.william.controllers;

import br.com.william.domain.day.Day;
import br.com.william.domain.owner.Owner;
import br.com.william.enums.PossibleHour;
import br.com.william.repositories.DayRepository;
import br.com.william.repositories.HourRepository;
import br.com.william.repositories.OwnerRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
class DayControllerTest {
    private static final String DAY = "2021-05-05";

    @Inject
    DayRepository dayRepository;

    @Inject
    HourRepository hourRepository;

    @Inject
    OwnerRepository ownerRepository;

    private Owner owner;

    private Day day;

    @BeforeEach
    @Transactional
    void setUp() {
        registerOwner();
        registerDay("2021-10-10");
    }

    @AfterEach
    @Transactional
    void tearDown() {
        ownerRepository.deleteAll();
        hourRepository.deleteAll();
        dayRepository.deleteAll();
    }

    @Test
    @DisplayName("Should register day")
    public void shouldRegisterDay() {
        given()
                .when()
                .post("/api/v1/day/register?day=" + DAY)
                .then()
                        .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @DisplayName("Should register day when null or empty")
    public void shouldNotRegisterDayWhenNullOrEmpty() {
        given()
                .when()
                .post("/api/v1/day/register?day=")
                .then()
                        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .body("status", CoreMatchers.is(Response.Status.BAD_REQUEST.getStatusCode()))
                        .body("message", CoreMatchers.is("Business exception"));
    }

    @Test
    @DisplayName("Should register day when already exists")
    public void shouldNotRegisterDayWhenAlreadyExists() {
        given()
                .when()
                .post("/api/v1/day/register?day=" + day.getDay())
                .then()
                        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .body("status", CoreMatchers.is(Response.Status.BAD_REQUEST.getStatusCode()))
                        .body("message", CoreMatchers.is("Business exception"));
    }

    @Test
    @DisplayName("Should not find day")
    public void shouldNotFindDay() {
        given()
                .when()
                .get("/api/v1/day?day=" + "2021-09-09")
                .then()
                        .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                        .body("status", CoreMatchers.is(Response.Status.NOT_FOUND.getStatusCode()))
                        .body("message", CoreMatchers.is("Object not found"));
    }

    @Test
    @DisplayName("Should not find day because hours already rented")
    @Transactional
    public void shouldNotFindDayBecauseHoursAlreadyRented() {
      day.getHours().stream().forEach(s -> s.updateRentHour());
        given()
                .when()
                .get("/api/v1/day?day=" + day.getDay())
                .then()
                        .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                        .body("status",  CoreMatchers.is(Response.Status.NOT_FOUND.getStatusCode()))
                        .body("message", CoreMatchers.is("Object not found"));
    }

    @Test
    @DisplayName("Should not update day")
    public void shouldNotUpdate() {
        given()
                .when()
                .get("/api/v1/day/" +  UUID.randomUUID() + "/" + UUID.randomUUID())
                .then()
                        .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("Should update day")
    public void shouldUpdateDay() {
        var firstHour =
                day.getHours().stream().findFirst().get();

        given()
                .when()
                .patch("/api/v1/day/rent/{externalId}/{externalIdOwner}" ,
                        firstHour.getExternalId(), owner.getExternalId())
                .then()
                        .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    private Day registerDay(String date) {
        day = new Day(date);
        var hour = PossibleHour.timeGenerator();
        day.addHours(hour);
        hourRepository.persist(hour);
        dayRepository.persist(day);
        return day;
    }

    private Owner registerOwner(){
         owner = new Owner(
                "Pedro",
                "teste.te@email.com",
                "TabCdeF1$#"
        );
        ownerRepository.persist(owner);
        return owner;
    }
}