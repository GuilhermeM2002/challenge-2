package br.com.challenge2.order.core.useCase

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord

interface GetProductsUseCase {
    fun getProducts(data: ConsumerRecord<String, GenericRecord>)
}