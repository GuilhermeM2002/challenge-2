package br.com.challenge2.product.controller

import br.com.challenge2.product.core.domain.Product
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime

@SpringBootTest
class ProductControllerTest {

    private lateinit var product : Product
    private lateinit var uri : String

    @BeforeEach
    fun setUp() {
        product = Product()
        product.setName("t-shirt")
        product.setPrice(100.0)
        product.setQuantity(1)
        product.setDescription("Blue t-shirt in cotton")

        uri = "http://localhost:8000/product"
    }

    @Test
    fun registerProduct() {
        given()
            .body(product)
            .contentType(ContentType.JSON)
            .`when`()
            .post(uri)
            .then()
            .statusCode(201)
            .log().all()
    }

    @Test
    fun editProduct() {
        product.setPrice(80.0)

        val id : Long = given()
            .body(product)
            .contentType(ContentType.JSON)
            .`when`()
            .post(uri)
            .then()
            .statusCode(201)
            .extract()
            .path<Long>("id")

        given()
            .body(product)
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .`when`()
            .put("$uri/{id}")
            .then()
            .statusCode(200)
            .log().all()
    }

    @Test
    fun removeProduct() {
        val id : Long = given()
            .body(product)
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
    fun findOneProduct() {
        val id : Long = given()
            .body(product)
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
            .body(matchesJsonSchemaInClasspath("schemas/ExpectedJsonSchemaProduct.json"))
            .log().all()
    }

    @Test
    fun findAllProduct() {
        given()
            .`when`()
            .get(uri)
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/ExpectedJsonSchemaAllProducts.json"))
            .log().all()
    }
}