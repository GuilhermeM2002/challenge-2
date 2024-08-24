package br.com.challenge2.client.core.domain

import br.com.challenge2.client.dto.ClientDto
import jakarta.persistence.*

@Entity
@Table(name = "client")
data class Client (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,

    @Column(name = "name", nullable = false)
    var name : String,

    @Column(name = "email", nullable = false, unique = true)
    var email : String,

    @OneToMany(mappedBy = "client", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var addresses: List<Address> = mutableListOf()
){
    fun updateByDto(dto : ClientDto){
        this.email = dto.email
        this.name = dto.name
    }
}
