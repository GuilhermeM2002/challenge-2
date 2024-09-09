package br.com.challenge2.order.core.domain

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "order")
class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id : Long? = null,

    @Column(name = "products", nullable = false)
    private var listOfProducts : Set<Long>,

    @Column(name = "email", nullable = false)
    private var clientEmail : String,

    @Column(name = "address", nullable = false)
    private var address : String,

    @Column(name = "date_order", nullable = false)
    private var date : OffsetDateTime
){
    // Getters
    fun getId(): Long? {
        return id
    }

    fun getListOfProducts(): Set<Long> {
        return listOfProducts
    }

    fun getClientEmail(): String {
        return clientEmail
    }

    fun getAddress(): String {
        return address
    }

    fun getDate(): OffsetDateTime {
        return date
    }

    // Setters
    fun setListOfProducts(listOfProducts: Set<Long>) {
        this.listOfProducts = listOfProducts
    }

    fun setClientEmail(clientEmail: String) {
        this.clientEmail = clientEmail
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun setDate(date: OffsetDateTime) {
        this.date = date
    }
}