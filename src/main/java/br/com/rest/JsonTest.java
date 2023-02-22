package br.com.rest;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    @Test
    public void primeiroNivelDoJson() {
        given()
                .when()
                .get("http://restapi.wcaquino.me/users/1")
                .then()
                .statusCode(200)
                .body("id", is(1));
    }

    @Test
    public void primeiroNivelDoJsonAlternative() {
        Response response = request(Method.GET, "http://restapi.wcaquino.me/users/1");

        assertEquals(new Integer(1), response.path("id"));

        JsonPath jsonPath = new JsonPath(response.asString());
        assertEquals(1, jsonPath.getInt("id"));

        int id = JsonPath.from(response.asString()).getInt("id");
        assertEquals(1, id);
    }

    @Test
    public void segundoNivel() {
        given()
                .when()
                .get("http://restapi.wcaquino.me/users/2")
                .then()
                .statusCode(200)
                .body("name", containsString("Joaquina"))
                .body("endereco.rua", is("Rua dos bobos"));
    }
    @Test
    public void verificarLista() {
        given()
            .when()
                .get("http://restapi.wcaquino.me/users/3")
            .then()
                .statusCode(200)
                .body("name", containsString("Ana"))
                .body("filhos.name", hasItems("Zezinho"));
    }
    @Test
    public void retornandoErroUsuario() {
        given()
            .when()
                .get("http://restapi.wcaquino.me/users/4")
            .then()
                .statusCode(404)
                .body("error", is("Usuário inexistente"));
    }

    @Test
    public void listaRaiz() {
        given()
            .when()
                .get("http://restapi.wcaquino.me/users")
            .then()
                .statusCode(200)
                .body("$", hasSize(3))
                .body("name", hasItems("João da Silva", "Maria Joaquina"))
                .body("age[1]", is(25));
    }

    @Test
    public void verificacoesAvancadas() {
        given()
            .when()
                .get("http://restapi.wcaquino.me/users")
            .then()
                .statusCode(200)
                .body("$", hasSize(3))
                .body("age.findAll{it <= 25}.size()", is(2));
    }

}
