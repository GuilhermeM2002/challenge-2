package br.com.challenge2.order.core.domain

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "order")
class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,

    @Column(name = "products", nullable = false)
    var listOfProducts : Set<Long>,

    @Column(name = "email", nullable = false)
    var clientEmail : String,

    @Column(name = "address", nullable = false)
    var address : String,

    @Column(name = "date_order", nullable = false)
    var date : OffsetDateTime
){
}