package br.com.rest;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;

public class EnviaDadosTest {
    @Test
    public void deveEnviarValorViaQuery() {
        given()
                .log().all()
            .when()
                .get("http://restapi.wcaquino.me/v2/users?format=xml")
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML);
    }

    @Test
    public void deveEnviarValorViaQueryViaParam() {
        given()
                .log().all()
                .queryParam("format", "xml")
                .queryParam("outra", "coisa")
            .when()
                .get("http://restapi.wcaquino.me/v2/users")
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
                .contentType(containsString("UTF-8"));
    }

    @Test
    public void deveEnviarValorViaHeader() {
        given()
                .log().all()
                .accept(ContentType.XML)
            .when()
                .get("http://restapi.wcaquino.me/v2/users")
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML);
    }
}
