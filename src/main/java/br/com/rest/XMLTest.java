package br.com.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class XMLTest {
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;

    @Test
    public void trabalhandoComXML() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.log(LogDetail.ALL);

        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200);
        responseSpecification = responseSpecBuilder.build();

        given()
                .spec(requestSpecification)
            .when()
                .get("http://restapi.wcaquino.me/usersXML/3")
            .then()
                // .statusCode(200)
                .spec(responseSpecification)
                .rootPath("user")
                .body("name", is("Ana Julia"))
                .body("@id", is("3"));
    }

    @Test
    public void xmlAvancado() {
        given()
            .when()
                .get("http://restapi.wcaquino.me/usersXML")
            .then()
                .statusCode(200)
                .body("users.user.size()", is(3))
                .body("users.user.@id", hasItems("3"));
    }

    @Test
    public void trabalhandoComXPath() {
        given()
            .when()
                .get("http://restapi.wcaquino.me/usersXML")
            .then()
                .statusCode(200)
                .body(hasXPath("count(/users/user)", is("3")));
    }
}
