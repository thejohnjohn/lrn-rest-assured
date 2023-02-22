package br.com.rest;

import io.restassured.matcher.RestAssuredMatchers;
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

    @Test
    public void naoDeveValidarSchemaXMLInvalido() {
        given()
                .log().all()
            .when()
                .get("https://restapi.wcaquino.me/invalidUsersXML")
            .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
    }
}
