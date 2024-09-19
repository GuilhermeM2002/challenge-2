package br.com.challenge2.product.application.dto

class OrderReceivedDto(
    var id : Long,
    var listOfProducts : Set<Long>
) {
}