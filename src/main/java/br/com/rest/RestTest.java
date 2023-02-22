package br.com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
public class RestTest {
    @Test
    public void outrasFormasDeRestAssured() {
        get("http://restapi.wcaquino.me/ola").then().statusCode(200);
        given()
            .when()
                .get("http://restapi.wcaquino.me/ola")
            .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void conhecendoMatchersHamcrest() {
        assertThat("Maria", is("Maria"));
    }

    @Test
    public void validarBody() {
        given()
            .when()
                .get("http://restapi.wcaquino.me/ola")
            .then()
                .statusCode(200)
                .body(is("Ola Mundo!"))
                .body(containsString("Mundo"));
    }
}