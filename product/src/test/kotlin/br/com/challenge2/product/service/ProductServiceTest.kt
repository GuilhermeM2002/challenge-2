package br.com.challenge2.product.service

import br.com.challenge2.product.application.dto.ProductDto
import br.com.challenge2.product.core.domain.Product
import br.com.challenge2.product.repository.ProductRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

@SpringBootTest
class ProductServiceTest {
    @Mock
    private lateinit var productRepository: ProductRepository
    @Mock
    private lateinit var mapper: ModelMapper
    @InjectMocks
    private lateinit var productService: ProductService

    private lateinit var productDto: ProductDto
    private lateinit var product: Product
    private var id : Long = 1L

    @BeforeEach
    fun setUp(){
        productDto = mock(ProductDto::class.java)
        product = mock(Product::class.java)
    }

    @Test
    fun `should persist product`() {
        `when`(mapper.map(productDto, Product::class.java)).thenReturn(product)
        `when`(mapper.map(product, ProductDto::class.java)).thenReturn(productDto)

        val result = productService.persistProduct(productDto)

        verify(productRepository).save(product)
        assertEquals(productDto, result)
    }

    @Test
    fun `should update product`() {
        `when`(productRepository.findById(id)).thenReturn(Optional.of(product))
        `when`(mapper.map(product, ProductDto::class.java)).thenReturn(productDto)

        val result = productService.updateProduct(productDto, id)

        verify(productRepository).save(product)
        assertEquals(productDto, result)
    }

    @Test
    fun `should delete product`() {
        `when`(productRepository.findById(id)).thenReturn(Optional.of(product))

        productService.deleteProduct(id)

        verify(productRepository).delete(product)
    }

    @Test
    fun `should find product by id`() {
        `when`(productRepository.findById(id)).thenReturn(Optional.of(product))
        `when`(mapper.map(product, ProductDto::class.java)).thenReturn(productDto)

        val result = productService.findProductById(id)

        assertEquals(productDto, result)
    }

    @Test
    fun `should find all products`() {
        val pageable: Pageable = mock(Pageable::class.java)
        val page = PageImpl(listOf(product))

        `when`(productRepository.findAll(pageable)).thenReturn(page)
        `when`(mapper.map(product, ProductDto::class.java)).thenReturn(productDto)

        val result = productService.findAllProduct(pageable)

        assertEquals(1, result.content.size)
        assertEquals(productDto, result.content[0])
    }
}