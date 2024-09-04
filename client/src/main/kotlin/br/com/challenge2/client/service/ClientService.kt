package br.com.challenge2.client.service

import br.com.challenge2.client.core.domain.Client
import br.com.challenge2.client.application.dto.ClientDto
import br.com.challenge2.client.repository.ClientRepository
import jakarta.persistence.EntityNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ClientService {
    @Autowired
    lateinit var clientRepository : ClientRepository
    @Autowired
    lateinit var mapper : ModelMapper

    fun persistClient(dto : ClientDto) : ClientDto {
        val client : Client = mapper.map(dto, Client::class.java)

        clientRepository.save(client)

        return mapper.map(client, ClientDto::class.java)
    }

    fun updateClient(dto : ClientDto, id : Long) : ClientDto {
        val clientUpdate = clientRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Client with ID $id not found") }

        clientUpdate.updateByDto(dto)
        clientRepository.save(clientUpdate)

        return mapper.map(clientUpdate, ClientDto::class.java)
    }

    fun deleteClient(id : Long){
        val clientDelete = clientRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Client with ID $id not found") }

        clientRepository.delete(clientDelete)
    }

    fun findClientByEmail(email : String) : ClientDto {
        val client : Client= clientRepository.findByEmail(email)

        return mapper.map(client, ClientDto::class.java)
    }
}