package br.com.challenge2.client.core.useCase

import org.apache.avro.generic.GenericRecord

interface OrderReceivedUseCase {
    fun orderReceived(data : GenericRecord)
}