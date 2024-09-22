package br.com.challenge2.client.controller

import br.com.challenge2.client.core.domain.Address
import br.com.challenge2.client.core.domain.Client
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class ClientControllerTest {
    private lateinit var client : Client
    private lateinit var uri : String

    @BeforeEach
    fun setUp() {
        client = Client()
        client.setName("João Souza")
        client.setEmail("email+${UUID.randomUUID()}@email.com")
        
        uri = "http://localhost:8000/client"
    }

    @Test
    fun registerClient() {
        given()
            .body(client)
            .contentType(ContentType.JSON)
            .`when`()
            .post(uri)
            .then()
            .statusCode(201)
            .log().all()
    }

    @Test
    fun editClient() {
        val id : Long = given()
            .body(client)
            .contentType(ContentType.JSON)
            .`when`()
            .post(uri)
            .then()
            .statusCode(201)
            .extract()
            .path<Long>("id")

        client.setEmail("emailemail@email.com")

        given()
            .body(client)
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .`when`()
            .put("$uri/{id}")
            .then()
            .statusCode(200)
            .log().all()
    }

    @Test
    fun removeClient() {
        val id : Long = given()
            .body(client)
            .contentType(ContentType.JSON)
            .`when`()
            .post(uri)
            .then()
            .statusCode(201)
            .extract()
            .path<Long>("id")

        given()
            .pathParam("id", id )
            .`when`()
            .delete("$uri/{id}")
            .then()
            .statusCode(204)
    }

    @Test
    fun findClient() {
        val email : String = given()
            .body(client)
            .contentType(ContentType.JSON)
            .`when`()
            .post(uri)
            .then()
            .statusCode(201)
            .extract()
            .path<String>("email")

        given()
            .pathParam("email", email)
            .`when`()
            .get("$uri/{email}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/ExpectedJsonSchemaClient.json"))
            .log().all()
    }
}