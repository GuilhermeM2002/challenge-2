package br.com.challenge2.order.core.useCase

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord

interface GetAddressUseCase {
    fun getAddress(data : ConsumerRecord<String, GenericRecord>)
}