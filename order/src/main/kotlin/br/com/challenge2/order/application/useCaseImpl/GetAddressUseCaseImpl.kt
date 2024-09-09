package br.com.challenge2.order.application.useCaseImpl

import br.com.challenge2.order.application.dto.ClientReceivedDto
import br.com.challenge2.order.core.domain.Order
import br.com.challenge2.order.core.useCase.GetAddressUseCase
import br.com.challenge2.order.repository.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class GetAddressUseCaseImpl : GetAddressUseCase{
    @Autowired
    private lateinit var orderRepository : OrderRepository

    @KafkaListener(topicPattern = "client", groupId = "group")
    override fun getAddress(data: ConsumerRecord<String, GenericRecord>) {
        val objectMapper = ObjectMapper()
        objectMapper.findAndRegisterModules()

        val jsonString = data.toString()
        val client: ClientReceivedDto = objectMapper.readValue(jsonString, ClientReceivedDto::class.java)

        val orderId : Long = data.key().toLong()
        val order : Order = orderRepository.findById(orderId).orElseThrow{ EntityNotFoundException() }

        val address : String = client.addressReceivedDto.city + ", " +
                client.addressReceivedDto.street + ", " +
                client.addressReceivedDto.state + ", " +
                client.addressReceivedDto.country + ", " +
                client.addressReceivedDto.zipcode
        order.setAddress(address)

        orderRepository.save(order)
    }
}