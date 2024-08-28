package br.com.challenge2.order.service

import br.com.challenge2.order.core.domain.Order
import br.com.challenge2.order.dto.OrderDto
import br.com.challenge2.order.repository.OrderRepository
import jakarta.persistence.EntityNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class OrderService {
    @Autowired
    lateinit var orderRepository : OrderRepository
    @Autowired
    lateinit var mapper : ModelMapper

    fun persistOrder(dto : OrderDto) : OrderDto{
        val order : Order = mapper.map(dto, Order::class.java)

        orderRepository.save(order)

        return mapper.map(order, OrderDto::class.java)
    }

    fun deleteOrder(id : Long){
        val clientDelete = orderRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Order with ID $id not found") }

        orderRepository.delete(clientDelete)
    }

    fun findOrderById(id : Long) : OrderDto{
        val order : Order= orderRepository.findById(id)
            .orElseThrow{ EntityNotFoundException("Order with id $id not found") }

        return mapper.map(order, OrderDto::class.java)
    }

    fun findAllOrder (pageable: Pageable) : Page<OrderDto>{
        val orders = orderRepository.findAll(pageable).map{
            order -> mapper.map(order, OrderDto::class.java)}

        return orders
    }
}