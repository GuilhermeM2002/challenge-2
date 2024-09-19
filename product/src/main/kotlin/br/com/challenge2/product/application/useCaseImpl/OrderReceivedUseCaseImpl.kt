package br.com.challenge2.product.application.useCaseImpl

import br.com.challenge2.product.application.dto.OrderReceivedDto
import br.com.challenge2.product.core.domain.Product
import br.com.challenge2.product.core.useCase.OrderReceivedUseCase
import br.com.challenge2.product.repository.ProductRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.apache.avro.generic.GenericData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Service
class OrderReceivedUseCaseImpl : OrderReceivedUseCase{
    @Autowired
    private lateinit var productRepository: ProductRepository
    @Autowired
    private lateinit var sendProducts : SendListOfProductsUseCaseImpl

    @KafkaListener(topicPattern = "product", groupId = "group")
    override fun orderReceived(data: GenericData) {
        val objectMapper = ObjectMapper()
        objectMapper.findAndRegisterModules()

        val jsonString = data.toString()
        val order: OrderReceivedDto = objectMapper.readValue(jsonString, OrderReceivedDto::class.java)

        val listOfProducts: MutableSet<Product> = mutableSetOf()

        for (item in order.listOfProducts) {
            val product = productRepository.findById(item).orElseThrow{ EntityNotFoundException() }
            product.let { listOfProducts.add(it) }
        }

        sendProducts.sendListOfProducts(order.id, listOfProducts)
    }
}