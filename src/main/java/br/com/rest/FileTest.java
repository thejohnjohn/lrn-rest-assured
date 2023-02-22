package br.com.rest;

import org.junit.jupiter.api.Test;

import java.io.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FileTest {
    @Test
    public void deveObrigarEnviarArquivo() {
        given()
                .log().all()
            .when()
                .get("https://restapi.wcaquino.me/upload")
            .then()
                .log().all()
                .statusCode(404)
                .body("error", is("Arquivo não enviado"));
    }

    @Test
    public void deveFazerUploadDeArquivo() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/users.pdf"))
            .when()
                .post("https://restapi.wcaquino.me/upload")
            .then()
                .log().all()
                .statusCode(200)
                .body("name", is("users.pdf"));
    }

    @Test
    public void naoDeveFazerUploadDeArquivoGrande() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/junit-3.8.1.jar"))
            .when()
                .post("https://restapi.wcaquino.me/upload")
            .then()
                .log().all()
                .time(lessThan(2000L))
                .statusCode(200);
    }

    @Test
    public void deveBaixarArquivo() throws IOException {
        byte[] image = given()
                .log().all()
            .when()
                .get("https://restapi.wcaquino.me/download")
            .then()
                .log().all()
                .statusCode(200)
                .extract().asByteArray()
        ;

        File imagem = new File("src/main/resources/file.jpg");
        OutputStream outputStream = new FileOutputStream(imagem);
        outputStream.write(image);
        outputStream.close();

        System.out.println(imagem.length());
    }
}
