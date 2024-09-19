package br.com.challenge2.product.core.useCase

import br.com.challenge2.product.core.domain.Product

interface SendListOfProductsUseCase {
    fun sendListOfProducts(id : Long, products : MutableSet<Product>)
}