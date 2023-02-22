package br.com.rest;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class VerbosTest {
    @Test
    public void deveSalvarUsuario() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"name\": \"Jose\", \"age\": 50}")
            .when()
                .post("https://restapi.wcaquino.me/users")
            .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("Jose"))
                .body("age", is(50));
    }

    @Test
    public void deveSalvarUsuarioUsandoMap () {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("name", "Usuario via Map");
        params.put("age", 25);

        given()
                .log().all()
                .contentType("application/json")
                .body(params)
            .when()
                .post("https://restapi.wcaquino.me/users")
            .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("Usuario via Map"))
                .body("age", is(25));
    }

    @Test
    public void deveSalvarUsuarioUsandoObjeto () {
        User user = new User("John", 45, 2300.00);

        given()
                .log().all()
                .contentType("application/json")
                .body(user)
            .when()
                .post("https://restapi.wcaquino.me/users")
            .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("John"))
                .body("age", is(45));
    }

    @Test
    public void deveDeserializarObjetoAoSalvarUsuario () {
        User user = new User("Jack", 25, 2300.00);

        User usuarioInserido = given()
                .log().all()
                .contentType("application/json")
                .body(user)
            .when()
                .post("https://restapi.wcaquino.me/users")
            .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(User.class);

        System.out.println(usuarioInserido);

        Assertions.assertEquals("Jack", usuarioInserido.getName());
    }

    @Test
    public void naoDeveSalvarUsuarioSemNome() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"age\": 50}")
            .when()
                .post("https://restapi.wcaquino.me/users")
            .then()
                .log().all()
                .statusCode(400)
                .body("id", is(nullValue()))
                .body("error", is("Name é um atributo obrigatório"));
    }

    @Test
    public void deveSalvarUsuarioXML() {
        given()
                .log().all()
                .contentType(ContentType.XML)
                .body("<user>\n" +
                        "   <name>Jose</name>\n" +
                        "   <age>50</age>\n" +
                        "</user>")
            .when()
                .post("https://restapi.wcaquino.me/usersXML")
            .then()
                .log().all()
                .statusCode(201)
                .body("user.@id", is(notNullValue()))
                .body("user.name", is("Jose"))
                .body("user.age", is("50"));
    }

    @Test
    public void deveSalvarUsuarioXMLUsandoObjeto() {
        User user = new User("Anne", 15, 150.00);

        given()
                .log().all()
                .contentType(ContentType.XML)
                .body(user)
            .when()
                .post("https://restapi.wcaquino.me/usersXML")
            .then()
                .log().all()
                .statusCode(201)
                .body("user.name", is("Anne"))
                .body("user.age", is("15"));
    }

    @Test
    public void deveDeserializarXMLAoSalvarUsando() {
        User user = new User("Anne", 15, 150.00);

        User usuarioInserido = given()
                .log().all()
                .contentType(ContentType.XML)
                .body(user)
            .when()
                .post("https://restapi.wcaquino.me/usersXML")
            .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(User.class);

        System.out.println(usuarioInserido);
    }

    @Test
    public void deveAtualizarUsuario() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"uSUARIO aLTERADO\", \"age\": 50}")
            .when()
                .put("https://restapi.wcaquino.me/users/1")
            .then()
                .log().all();
    }

    @Test
    public void deveCustomizarURL() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"uSUARIO aLTERADO\", \"age\": 50}")
                .pathParam("entidade", "users")
                .pathParam("users", "1")
            .when()
                .put("https://restapi.wcaquino.me/{entidade}}/{userId}}")
            .then()
                .log().all();
    }

    @Test
    public void deveRemoverUsuario() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
            .when()
                .delete("https://restapi.wcaquino.me/users/1")
            .then()
                .log().all();
    }

    @Test
    public void naoDeveRemoverUsuarioInexistente() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
            .when()
                .delete("https://restapi.wcaquino.me/users/100")
            .then()
                .log().all()
                .statusCode(400);
    }
}
