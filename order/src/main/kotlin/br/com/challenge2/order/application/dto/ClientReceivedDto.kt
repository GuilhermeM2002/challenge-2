package br.com.challenge2.order.application.dto

class ClientReceivedDto(
    val id : Long? = null,
    var name : String,
    var email : String,
    var addressReceivedDto: AddressReceivedDto
) {
}