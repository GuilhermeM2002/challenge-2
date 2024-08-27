package br.com.challenge2.order.controller

import br.com.challenge2.order.dto.OrderDto
import br.com.challenge2.order.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/order")
class OrderController {
    @Autowired
    lateinit var service : OrderService

    @PostMapping
    @Transactional
    fun registerOrder(@RequestBody dto : OrderDto, builder : UriComponentsBuilder) : ResponseEntity<OrderDto> {
        val uri = builder.path("/order/{id}").buildAndExpand(dto.id).toUri()
        val order = service.persistOrder(dto)

        return ResponseEntity.created(uri).body(order)
    }

    @DeleteMapping
    @Transactional
    fun removeOrder(@RequestParam id : Long) : ResponseEntity<Void> {
        service.deleteOrder(id)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun findOneOrder(@RequestParam id : Long) : ResponseEntity<OrderDto> {
        val order : OrderDto = service.findOrderById(id)

        return ResponseEntity.ok(order)
    }

    @GetMapping
    fun findAllOrder(pageable: Pageable) : ResponseEntity<Page<OrderDto>>{
        val allOrder = service.findAllOrder(pageable)
        
        return ResponseEntity.ok(allOrder)
    }
}