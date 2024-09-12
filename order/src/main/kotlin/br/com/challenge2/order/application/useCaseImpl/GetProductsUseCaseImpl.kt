package br.com.challenge2.order.application.useCaseImpl

import br.com.challenge2.order.application.dto.ProductsReceivedDto
import br.com.challenge2.order.core.domain.Order
import br.com.challenge2.order.core.useCase.GetProductsUseCase
import br.com.challenge2.order.repository.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class GetProductsUseCaseImpl : GetProductsUseCase {
    @Autowired
    private lateinit var orderRepository: OrderRepository

    @KafkaListener(topicPattern = "product", groupId = "group")
    override fun getProducts(data: ConsumerRecord<String, GenericRecord>) {
        val objectMapper = ObjectMapper()
        objectMapper.findAndRegisterModules()

        val jsonString = data.toString()
        val productsReceived: ProductsReceivedDto = objectMapper.readValue(jsonString, ProductsReceivedDto::class.java)

        val orderId : Long = data.key().toLong()
        val order : Order = orderRepository.findById(orderId).orElseThrow{ EntityNotFoundException() }

        val totalPrice = productsReceived.products.sumOf { it.price }

        order.setTotalPrice(totalPrice)
        orderRepository.save(order)
    }
}