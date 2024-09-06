package br.com.challenge2.product.core.useCase

import org.apache.avro.generic.GenericData

interface OrderReceivedUseCase {
    fun orderReceived(data : GenericData)
}