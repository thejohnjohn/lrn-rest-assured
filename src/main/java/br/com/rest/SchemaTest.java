package br.com.rest;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXParseException;

import static io.restassured.RestAssured.given;

public class SchemaTest {
    @Test
    public void deveValidarSchemaXML() {
        given()
                .log().all()
            .when()
                .get("https://restapi.wcaquino.me/usersXML")
            .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
    }

    @Test()
    public void naoDeveValidarSchemaXMLInvalido() {
        Assertions.assertThrows(SAXParseException.class, () -> {
            given()
                .log().all()
            .when()
                .get("https://restapi.wcaquino.me/invalidUsersXML")
            .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
        });
    }

    @Test
    public void deveValidarSchemaJSON() {
        given()
                .log().all()
            .when()
                .get("https://restapi.wcaquino.me/users")
            .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"));
    }
}
