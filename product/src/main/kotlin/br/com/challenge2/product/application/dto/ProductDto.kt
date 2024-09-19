package br.com.challenge2.product.application.dto

class ProductDto (
    var id : Long?,
    var name : String,
    var description : String,
    var price : Double,
    var quantity : Int
){
    constructor() : this(null, "", "", 0.0, 0)
}