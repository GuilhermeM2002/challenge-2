package br.com.challenge2.product.service

import br.com.challenge2.product.core.domain.Product
import br.com.challenge2.product.dto.ProductDto
import br.com.challenge2.product.repository.ProductRepository
import jakarta.persistence.EntityNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService {
    @Autowired
    lateinit var productRepository : ProductRepository
    @Autowired
    lateinit var mapper : ModelMapper

    fun persistProduct(dto : ProductDto) : ProductDto{
        val product : Product = mapper.map(dto, Product::class.java)

        productRepository.save(product)

        return mapper.map(product, ProductDto::class.java)
    }

    fun updateProduct(dto : ProductDto, id : Long) : ProductDto{
        val productUpdate = productRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Product with ID $id not found") }

        productUpdate.updateProduct(dto)
        productRepository.save(productUpdate)

        return mapper.map(productUpdate, ProductDto::class.java)
    }

    fun deleteProduct(id : Long){
        val productDelete = productRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Product with ID $id not found") }

        productRepository.delete(productDelete)
    }

    fun findProductById(id : Long) : ProductDto{
        val product : Product = productRepository.findById(id)
            .orElseThrow{ EntityNotFoundException("Product with ID $id not found") }

        return mapper.map(product, ProductDto::class.java)
    }

    fun findAllProduct (pageable: Pageable) : Page<ProductDto> {
        val products = productRepository.findAll(pageable).map{
                product -> mapper.map(product, ProductDto::class.java)}

        return products
    }
}