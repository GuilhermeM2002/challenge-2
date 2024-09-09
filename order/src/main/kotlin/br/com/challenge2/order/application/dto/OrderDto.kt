package br.com.challenge2.order.application.dto

import java.time.OffsetDateTime

class OrderDto(
    val id : Long? = null,
    var listOfProducts : Set<Long>,
    var clientEmail : String,
    var date : OffsetDateTime
) {
}