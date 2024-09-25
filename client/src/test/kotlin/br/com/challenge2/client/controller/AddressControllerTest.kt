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
class AddressControllerTest {
    private lateinit var addressJson : String
    private lateinit var uri : String

    @BeforeEach
    fun setUp() {
        val client = getClient()

        addressJson = """
    {
        "street": "Av. Paulista",
        "city": "São Paulo",
        "state": "São Paulo",
        "country": "Brazil",
        "zipcode": "01310-930",
        "client": {
            "id": ${client.getId()},
            "name": "${client.getName()}",
            "email": "${client.getEmail()}"
        }
    }
    """

        uri = "http://localhost:8000/address"
    }

    fun getClient(): Client {
        val clients: List<Client> = given()
            .`when`()
            .get("http://localhost:8000/client")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getList("content", Client::class.java)

        return clients.first()
    }

    @Test
    fun registerAddress() {
        given()
            .body(addressJson)
            .contentType(ContentType.JSON)
            .`when`()
            .post(uri)
            .then()
            .log().all()
            .statusCode(201)
    }

    @Test
    fun editAddress() {
        val id : Long = given()
            .body(addressJson)
            .contentType(ContentType.JSON)
            .`when`()
            .post(uri)
            .then()
            .statusCode(201)
            .extract()
            .path<Long>("id")

        given()
            .body(addressJson)
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .`when`()
            .put("$uri/{id}")
            .then()
            .log().all()
            .statusCode(200)
    }

    @Test
    fun removeAddress() {
        val id : Long = given()
            .body(addressJson)
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
    fun findOneAddress() {
        val id : Long = given()
            .body(addressJson)
            .contentType(ContentType.JSON)
            .`when`()
            .post(uri)
            .then()
            .statusCode(201)
            .extract()
            .path<Long>("id")

        given()
            .pathParam("id", id)
            .`when`()
            .get("$uri/{id}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/ExpectedJsonSchemaAddress.json"))
            .log().all()
    }

    @Test
    fun findAllAddress() {
        given()
            .`when`()
            .get(uri)
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/ExpectedJsonSchemaAllAddress.json"))
            .log().all()
    }
}