package br.com.challenge2.client.application.dto

import java.time.OffsetDateTime

class OrderReceivedDto(
    var id : Long,
    var listOfProducts : Set<Long>,
    var clientEmail : String,
    var address : String? = null,
    var totalPrice : Double,
    var date : OffsetDateTime
) {
}