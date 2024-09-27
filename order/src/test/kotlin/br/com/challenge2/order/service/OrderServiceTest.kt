package br.com.challenge2.order.service

import br.com.challenge2.order.application.dto.OrderDto
import br.com.challenge2.order.avro.OrderAvro
import br.com.challenge2.order.core.domain.Order
import br.com.challenge2.order.repository.OrderRepository
import jakarta.persistence.EntityNotFoundException
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
import org.springframework.kafka.core.KafkaTemplate
import java.time.OffsetDateTime
import java.util.*

@SpringBootTest
class OrderServiceTest {
    @Mock
    private lateinit var orderRepository: OrderRepository
    @Mock
    private lateinit var mapper: ModelMapper
    @Mock
    private lateinit var kafkaTemplate: KafkaTemplate<String, OrderAvro>
    @InjectMocks
    private lateinit var orderService: OrderService

    private lateinit var orderDto: OrderDto
    private lateinit var order: Order
    private var id: Long = 1L

    @BeforeEach
    fun setUp(){
        orderDto = mock(OrderDto::class.java)
        order = mock(Order::class.java)
    }

    @Test
    fun `should persist order and send message`() {
        val savedOrder = Order().apply {
            setId(1L)
            setDate(OffsetDateTime.now())
            setClientEmail("email@example.com")
            setListOfProducts(setOf(1L, 2L))
        }

        val orderAvro = OrderAvro().apply {
            id = savedOrder.getId()
            date = savedOrder.getDate().toInstant()
            clientEmail = savedOrder.getClientEmail()
            listOfProducts = savedOrder.getListOfProducts().toMutableList()
        }

        `when`(mapper.map(orderDto, Order::class.java)).thenReturn(order)
        `when`(orderRepository.save(order)).thenReturn(savedOrder)
        `when`(mapper.map(savedOrder, OrderDto::class.java)).thenReturn(orderDto)

        val result = orderService.persistOrder(orderDto)

        assertEquals(orderDto, result)
        verify(orderRepository).save(order)
        verify(kafkaTemplate).send("order-sent", orderAvro.clientEmail, orderAvro)
    }

    @Test
    fun `should delete order by id`() {
        `when`(orderRepository.findById(id)).thenReturn(Optional.of(order))

        orderService.deleteOrder(id)

        verify(orderRepository).findById(id)
        verify(orderRepository).delete(order)
    }

    @Test
    fun `should throw exception when order not found`() {
        `when`(orderRepository.findById(id)).thenReturn(Optional.empty())

        val exception = assertThrows(EntityNotFoundException::class.java) {
            orderService.deleteOrder(id)
        }
        assertEquals("Order with ID $id not found", exception.message)
    }

    @Test
    fun `should find order by id`() {
        `when`(orderRepository.findById(id)).thenReturn(Optional.of(order))
        `when`(mapper.map(order, OrderDto::class.java)).thenReturn(orderDto)

        val result = orderService.findOrderById(id)

        assertEquals(orderDto, result)
        verify(orderRepository).findById(id)
    }

    @Test
    fun `should find all orders`() {
        val pageable: Pageable = mock(Pageable::class.java)
        val orders = listOf(order)
        val page = PageImpl(orders)

        `when`(orderRepository.findAll(pageable)).thenReturn(page)
        `when`(mapper.map(any(Order::class.java), eq(OrderDto::class.java))).thenReturn(orderDto)

        val result = orderService.findAllOrder(pageable)

        assertNotNull(result)
        assertEquals(1, result.content.size)
        verify(orderRepository).findAll(pageable)
    }
}