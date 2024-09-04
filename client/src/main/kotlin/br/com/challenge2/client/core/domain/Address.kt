package br.com.challenge2.client.core.domain

import jakarta.persistence.*

@Entity
@Table(name = "address")
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

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
    private var client: Client? = null
){
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
}