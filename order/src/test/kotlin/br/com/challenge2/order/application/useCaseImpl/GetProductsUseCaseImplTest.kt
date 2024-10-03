package br.com.challenge2.order.application.useCaseImpl

import br.com.challenge2.order.core.domain.Order
import br.com.challenge2.order.repository.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.Test

@SpringBootTest
class GetProductsUseCaseImplTest{
    @Mock
    private lateinit var orderRepository: OrderRepository

    @InjectMocks
    private lateinit var getProductsUseCaseImpl: GetProductsUseCaseImpl

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        objectMapper = ObjectMapper().findAndRegisterModules()
    }

    @Test
    fun `should update order with total price when products are received`() {
        val orderId = 123L
        val mockOrder = mock(Order::class.java)
        val mockData = mock(ConsumerRecord::class.java) as ConsumerRecord<String, GenericRecord>

        `when`(mockData.key()).thenReturn(orderId.toString())

        val productsJson = """{
            "products": [
                { "name": "Product A", "description": "A sample product", "price": 10.0 },
                { "name": "Product B", "description": "Another sample product", "price": 20.0 },
                { "name": "Product C", "description": "Third product", "price": 30.0 }
            ]
        }"""


        `when`(mockData.toString()).thenReturn(productsJson)

        `when`(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder))

        getProductsUseCaseImpl.getProducts(mockData)

        verify(mockOrder).setTotalPrice(60.0)
        verify(orderRepository).save(mockOrder)
    }

}