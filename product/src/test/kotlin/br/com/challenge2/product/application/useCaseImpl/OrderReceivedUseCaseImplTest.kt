package br.com.challenge2.product.application.useCaseImpl

import br.com.challenge2.product.application.dto.OrderReceivedDto
import br.com.challenge2.product.core.domain.Product
import br.com.challenge2.product.repository.ProductRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.apache.avro.generic.GenericData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.assertFailsWith

@SpringBootTest
class OrderReceivedUseCaseImplTest{
    @Mock
    private lateinit var productRepository: ProductRepository
    @Mock
    private lateinit var sendListOfProductsUseCaseImpl: SendListOfProductsUseCaseImpl
    @InjectMocks
    private lateinit var orderReceivedUseCaseImpl: OrderReceivedUseCaseImpl

    private lateinit var objectMapper: ObjectMapper
    private lateinit var data: GenericData
    private lateinit var orderReceivedDto: OrderReceivedDto
    private lateinit var product: Product

    private val productId = 1L
    private val orderId = 123L

    @BeforeEach
    fun setUp() {
        objectMapper = ObjectMapper().apply { findAndRegisterModules() }
        product = mock(Product::class.java)

        orderReceivedDto = OrderReceivedDto(
            id = orderId,
            listOfProducts = setOf(productId)
        )

        data = mock(GenericData::class.java)
        `when`(data.toString()).thenReturn(objectMapper.writeValueAsString(orderReceivedDto))
    }

    @Test
    fun `orderReceived should send products when all products are found`() {
        `when`(productRepository.findById(productId)).thenReturn(Optional.of(product))

        orderReceivedUseCaseImpl.orderReceived(data)

        verify(productRepository).findById(productId)
        verify(sendListOfProductsUseCaseImpl).sendListOfProducts(orderId, mutableSetOf(product))
    }

    @Test
    fun `orderReceived should throw EntityNotFoundException when product is not found`() {
        `when`(productRepository.findById(productId)).thenReturn(Optional.empty())

        assertFailsWith<EntityNotFoundException> {
            orderReceivedUseCaseImpl.orderReceived(data)
        }

        verify(productRepository).findById(productId)
        verify(sendListOfProductsUseCaseImpl, never()).sendListOfProducts(anyLong(), anySet())
    }
}