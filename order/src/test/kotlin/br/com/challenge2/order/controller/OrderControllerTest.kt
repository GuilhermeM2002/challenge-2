package br.com.challenge2.order.controller

import br.com.challenge2.order.core.domain.Order
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime

@SpringBootTest
class OrderControllerTest {

    private lateinit var order : Order
    private lateinit var uri : String

    @BeforeEach
    fun setUp() {
        order = Order()
        order.setClientEmail("client@example.com")
        order.setListOfProducts(setOf(1L, 2L, 3L))
        order.setDate(OffsetDateTime.now())

        uri = "http://localhost:8000/order"
    }

    @Test
    fun registerOrder() {
        given()
            .body(order)
            .contentType(ContentType.JSON)
            .`when`()
            .post(uri)
            .then()
            .statusCode(201)
            .log().all()
    }

    @Test
    fun removeOrder() {
        val id : Long = given()
            .body(order)
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
    fun findOneOrder() {
        val id : Long = given()
            .body(order)
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
            .body(matchesJsonSchemaInClasspath("schemas/ExpectedJsonSchemaOrder.json"))
            .log().all()
    }

    @Test
    fun findAllOrder() {
        given()
            .`when`()
            .get(uri)
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/ExpectedJsonSchemaAllOrder.json"))
            .log().all()
    }
}