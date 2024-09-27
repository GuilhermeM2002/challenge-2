package br.com.challenge2.client.service

import br.com.challenge2.client.repository.ClientRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ClientServiceTest {
    @Mock
    private lateinit var clientRepository : ClientRepository

    @Test
    fun persistClient() {
    }

    @Test
    fun updateClient() {
    }

    @Test
    fun deleteClient() {
    }

    @Test
    fun findClientByEmail() {
    }

    @Test
    fun findAllClient() {
    }
}