package br.com.challenge2.client.core.domain

import br.com.challenge2.client.application.dto.ClientDto
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "client")
data class Client (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id : Long?,

    @Column(name = "name", nullable = false)
    private var name : String,

    @Column(name = "email", nullable = false, unique = true)
    private var email : String,

    @OneToMany(mappedBy = "client", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonManagedReference
    private var addresses: List<Address> = mutableListOf()
){

    constructor() : this(null, "", "", mutableListOf())

    // Getters
    fun getId(): Long? {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getEmail(): String {
        return email
    }

    fun getAddresses(): List<Address> {
        return addresses
    }

    // Setters
    fun setId(id: Long){
        this.id = id
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setAddresses(addresses: List<Address>) {
        this.addresses = addresses
    }

    fun updateByDto(dto: ClientDto) {
        this.email = dto.email
        this.name = dto.name
    }
}
