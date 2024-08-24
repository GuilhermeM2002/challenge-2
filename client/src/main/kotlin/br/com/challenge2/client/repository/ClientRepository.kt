package br.com.challenge2.client.repository

import br.com.challenge2.client.core.domain.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long>{
    fun findByEmail(email : String) : Client
}