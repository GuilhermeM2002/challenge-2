package br.com.challenge2.order.application.useCaseImpl

import br.com.challenge2.order.core.domain.Order
import br.com.challenge2.order.repository.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class GetAddressUseCaseImplTest {
    @Mock
    lateinit var orderRepository: OrderRepository

    @InjectMocks
    lateinit var getAddressUseCaseImpl: GetAddressUseCaseImpl

    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        objectMapper = ObjectMapper().findAndRegisterModules()
    }

    @Test
    fun `should update order with address when client data is received`() {
        val orderId = 123L
        val mockData = mock(ConsumerRecord::class.java) as ConsumerRecord<String, GenericRecord>
        `when`(mockData.key()).thenReturn(orderId.toString())

        val clientJson = """{
    "name": "John Doe",
    "email": "email@email.com",
    "addressReceivedDto": {
        "city": "CityName",
        "street": "StreetName",
        "state": "StateName",
        "country": "CountryName",
        "zipcode": "12345"
    }
}"""

        `when`(mockData.toString()).thenReturn(clientJson)

        val order = Order()
        `when`(orderRepository.findById(orderId)).thenReturn(Optional.of(order))

        getAddressUseCaseImpl.getAddress(mockData)

        val expectedAddress = "CityName, StreetName, StateName, CountryName, 12345"
        verify(orderRepository).findById(orderId)
        verify(orderRepository).save(order)
        assert(order.getAddress() == expectedAddress)
    }
}