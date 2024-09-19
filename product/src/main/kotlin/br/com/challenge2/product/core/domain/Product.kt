package br.com.challenge2.product.core.domain

import br.com.challenge2.product.application.dto.ProductDto
import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id : Long?,

    @Column(name = "name", nullable = false)
    private var name : String,

    @Column(name = "description", nullable = false)
    private var description : String,

    @Column(name = "price", nullable = false)
    private var price : Double,

    @Column(name = "quantity", nullable = false)
    private var quantity : Int
){
    constructor() : this(null, "", "", 0.0, 0)

    // Getters
    fun getId(): Long? = id
    fun getName(): String = name
    fun getDescription(): String = description
    fun getPrice(): Double = price
    fun getQuantity(): Int = quantity

    // Setters
    fun setName(name: String) {
        this.name = name
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setPrice(price: Double) {
        this.price = price
    }

    fun setQuantity(quantity: Int) {
        this.quantity = quantity
    }

    fun updateProduct(dto : ProductDto) {
        this.name = dto.name
        this.description = dto.description
        this.price = dto.price
        this.quantity = dto.quantity
    }
}