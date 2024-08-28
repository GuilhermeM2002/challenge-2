package br.com.challenge2.product.dto

class ProductDto (
    val id : Long? = null,
    var name : String,
    var description : String,
    var price : Double,
    var quantity : Int
){
}