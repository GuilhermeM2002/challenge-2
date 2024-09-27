package br.com.challenge2.order.core.domain

import br.com.challenge2.order.infra.converter.SetOfLongsConverter
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "t_order")
class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id : Long?,

    @Column(name = "products", nullable = false)
    @Convert(converter = SetOfLongsConverter::class)
    private var listOfProducts : Set<Long>,

    @Column(name = "email", nullable = false)
    private var clientEmail : String,

    @Column(name = "address")
    private var address : String,

    @Column(name = "price")
    private var totalPrice : Double,

    @Column(name = "date_order", nullable = false)
    private var date : OffsetDateTime
){
    constructor() : this(
        id = null,
        listOfProducts = emptySet(),
        clientEmail = "",
        address = "",
        totalPrice = 0.0,
        date = OffsetDateTime.now()
    )
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

    fun getTotalPrice(): Double {
        return totalPrice
    }

    fun getDate(): OffsetDateTime {
        return date
    }

    // Setters
    fun setId(id: Long?){
        this.id = id
    }

    fun setListOfProducts(listOfProducts: Set<Long>) {
        this.listOfProducts = listOfProducts
    }

    fun setClientEmail(clientEmail: String) {
        this.clientEmail = clientEmail
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun setTotalPrice(totalPrice : Double) {
        this.totalPrice = totalPrice
    }

    fun setDate(date: OffsetDateTime) {
        this.date = date
    }
}