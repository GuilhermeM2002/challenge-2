package br.com.challenge2.product.core.domain

import br.com.challenge2.product.dto.ProductDto
import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,

    @Column(name = "name", nullable = false)
    var name : String,

    @Column(name = "description", nullable = false)
    var description : String,

    @Column(name = "price", nullable = false)
    var price : Double,

    @Column(name = "quantity", nullable = false)
    var quantity : Int
){
    fun updateProduct(dto : ProductDto) {
        this.name = dto.name
        this.description = dto.description
        this.price = dto.price
        this.quantity = dto.quantity
    }
}