package br.com.challenge2.order.dto

import java.time.OffsetDateTime

class OrderDto(
    val id : Long? = null,
    var listOfProducts : Set<Long>,
    var clientEmail : String,
    var date : OffsetDateTime
) {
}