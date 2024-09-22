package br.com.challenge2.client.core.domain

import br.com.challenge2.client.application.dto.AddressDto
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "address")
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long?,

    @Column(name = "street", nullable = false)
    private var street: String,

    @Column(name = "city", nullable = false)
    private var city: String,

    @Column(name = "state", nullable = false)
    private var state: String,

    @Column(name = "zipcode", nullable = false)
    private var zipcode: String,

    @Column(name = "country", nullable = false)
    private var country: String,

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference
    private var client: Client?
){

    constructor() : this(null, "", "", "", "", "", null)

    // Getters
    fun getId(): Long? {
        return id
    }

    fun getStreet(): String {
        return street
    }

    fun getCity(): String {
        return city
    }

    fun getState(): String {
        return state
    }

    fun getZipcode(): String {
        return zipcode
    }

    fun getCountry(): String {
        return country
    }

    fun getClient(): Client? {
        return client
    }

    // Setters
    fun setStreet(street: String) {
        this.street = street
    }

    fun setCity(city: String) {
        this.city = city
    }

    fun setState(state: String) {
        this.state = state
    }

    fun setZipcode(zipcode: String) {
        this.zipcode = zipcode
    }

    fun setCountry(country: String) {
        this.country = country
    }

    fun setClient(client: Client?) {
        this.client = client
    }

    fun updateAddress(dto : AddressDto){
        street = dto.street
        city = dto.city
        state = dto.state
        zipcode = dto.zipcode
        country = dto.country
    }
}