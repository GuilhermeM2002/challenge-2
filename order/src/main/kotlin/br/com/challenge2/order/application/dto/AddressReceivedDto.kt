package br.com.challenge2.order.application.dto

class AddressReceivedDto(
    val id: Long? = null,
    var street: String,
    var city: String,
    var state: String,
    var zipcode: String,
    var country: String,
) {
}