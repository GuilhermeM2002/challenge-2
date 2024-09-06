package br.com.challenge2.product.controller

import br.com.challenge2.product.application.dto.ProductDto
import br.com.challenge2.product.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/product")
class ProductController {
    @Autowired
    private lateinit var service : ProductService

    @PostMapping
    @Transactional
    fun registerProduct(@RequestBody dto : ProductDto, builder : UriComponentsBuilder) : ResponseEntity<ProductDto> {
        val uri = builder.path("/product/{id}").buildAndExpand(dto.id).toUri()
        val product = service.persistProduct(dto)

        return ResponseEntity.created(uri).body(product)
    }

    @PutMapping
    @Transactional
    fun editProduct(@RequestBody dto : ProductDto, @RequestParam id : Long) : ResponseEntity<ProductDto>{
        val product : ProductDto = service.updateProduct(dto, id)

        return ResponseEntity.ok(product)
    }

    @DeleteMapping
    @Transactional
    fun removeProduct(@RequestParam id : Long) : ResponseEntity<Void> {
        service.deleteProduct(id)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun findOneProduct(@RequestParam id : Long) : ResponseEntity<ProductDto> {
        val product : ProductDto = service.findProductById(id)

        return ResponseEntity.ok(product)
    }

    @GetMapping
    fun findAllProduct(pageable: Pageable) : ResponseEntity<Page<ProductDto>> {
        val allProduct = service.findAllProduct(pageable)

        return ResponseEntity.ok(allProduct)
    }
}