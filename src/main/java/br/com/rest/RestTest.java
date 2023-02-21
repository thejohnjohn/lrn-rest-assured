package br.com.rest;

public class Main {
    @Test
    public void outrasFormasDeRestAssured(){
        get("http://restapi.wcaquino.me/ola").then().statusCode(200);
        given()
            .when()
                .get("http://restapi.wcaquino.me/ola")
            .then()
                .statusCode(200);
    }
}