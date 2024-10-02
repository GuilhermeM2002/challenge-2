package br.com.challenge2.client.application.useCaseImpl

import br.com.challenge2.client.avro.ClientAvro
import br.com.challenge2.client.core.domain.Address
import br.com.challenge2.client.core.domain.Client
import br.com.challenge2.client.repository.ClientRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.avro.generic.GenericRecord
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
import kotlin.test.Test

@SpringBootTest
class OrderReceivedUseCaseImplTest{
    @Mock
    lateinit var clientRepository: ClientRepository

    @Mock
    lateinit var kafkaTemplate: KafkaTemplate<String, ClientAvro>

    @Mock
    lateinit var mapper: ModelMapper

    @InjectMocks
    lateinit var orderReceivedUseCaseImpl: OrderReceivedUseCaseImpl

    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        objectMapper = ObjectMapper().findAndRegisterModules()
    }

    @Test
    fun `should process order and send clientAvro message`() {
        val mockData = mock(GenericRecord::class.java)
        val orderJson = """{
            "clientEmail": "test@example.com",
            "orderId": 123
        }"""
        `when`(mockData.toString()).thenReturn(orderJson)

        val client = Client(id = 1L, name = "Test Client", email = "test@example.com", addresses = listOf())
        `when`(clientRepository.findByEmail("test@example.com")).thenReturn(client)

        val clientAvro = ClientAvro()
        `when`(mapper.map(any(Address::class.java), eq(br.com.challenge2.client.avro.Address::class.java)))
            .thenReturn(br.com.challenge2.client.avro.Address())

        orderReceivedUseCaseImpl.orderReceived(mockData)

        verify(clientRepository).findByEmail("test@example.com")
        verify(kafkaTemplate).send(eq("client"), eq("123"), any(ClientAvro::class.java))
    }
}