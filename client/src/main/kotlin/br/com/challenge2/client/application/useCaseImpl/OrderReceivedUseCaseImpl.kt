package br.com.challenge2.client.application.useCaseImpl

import br.com.challenge2.client.application.dto.OrderReceivedDto
import br.com.challenge2.client.avro.ClientAvro
import br.com.challenge2.client.core.domain.Address
import br.com.challenge2.client.core.domain.Client
import br.com.challenge2.client.core.useCase.OrderReceivedUseCase
import br.com.challenge2.client.repository.ClientRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.avro.generic.GenericRecord
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class OrderReceivedUseCaseImpl : OrderReceivedUseCase {
    @Autowired
    lateinit var clientRepository: ClientRepository
    @Autowired
    lateinit var kafkaTemplate : KafkaTemplate<String, ClientAvro>
    @Autowired
    lateinit var mapper : ModelMapper

    @KafkaListener(topicPattern = "order-sent", groupId = "group")
    override fun orderReceived(data : GenericRecord){
        val objectMapper = ObjectMapper()
        objectMapper.findAndRegisterModules()

        val jsonString = data.toString()
        val order: OrderReceivedDto = objectMapper.readValue(jsonString, OrderReceivedDto::class.java)
        val client : Client = clientRepository.findByEmail(order.clientEmail)

        val clientAvro = ClientAvro()
        clientAvro.id = client.getId()
        clientAvro.name = client.getName()
        clientAvro.email = client.getEmail()
        clientAvro.addresses = client.getAddresses()
            .map { address: Address ->  mapper
                .map(address, br.com.challenge2.client.avro.Address::class.java) }

        kafkaTemplate.send("client", order.orderId.toString(), clientAvro)
    }
}