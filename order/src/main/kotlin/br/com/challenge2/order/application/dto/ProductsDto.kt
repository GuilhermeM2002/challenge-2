package br.com.challenge2.order.application.dto

class ProductsDto(
    val id : Long? = null,
    var name : String,
    var description : String,
    var price : Double,
    var quantity : Int
) {
}