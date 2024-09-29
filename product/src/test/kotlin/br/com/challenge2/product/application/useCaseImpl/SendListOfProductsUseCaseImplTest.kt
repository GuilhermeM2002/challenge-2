package br.com.challenge2.product.application.useCaseImpl

import br.com.challenge2.product.avro.ProductAvro
import br.com.challenge2.product.core.domain.Product
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate

@SpringBootTest
class SendListOfProductsUseCaseImplTest {
    @Mock
    private lateinit var kafkaTemplate: KafkaTemplate<String, List<ProductAvro>>

    @Mock
    private lateinit var mapper: ModelMapper

    @InjectMocks
    private lateinit var sendListOfProductsUseCaseImpl: SendListOfProductsUseCaseImpl

    private lateinit var product: Product
    private lateinit var productAvro: ProductAvro
    private var id: Long = 1L

    @BeforeEach
    fun setUp() {
        product = mock(Product::class.java)
        productAvro = mock(ProductAvro::class.java)
    }

    @Test
    fun sendListOfProducts() {
        val products = mutableSetOf(product)
        val productsAvro = listOf(productAvro)

        `when`(mapper.map(any(Product::class.java), eq(ProductAvro::class.java))).thenReturn(productAvro)

        sendListOfProductsUseCaseImpl.sendListOfProducts(id, products)

        verify(kafkaTemplate).send("product", id.toString(), productsAvro)
    }
}