package br.com.challenge2.order.repository

import br.com.challenge2.order.core.domain.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
}