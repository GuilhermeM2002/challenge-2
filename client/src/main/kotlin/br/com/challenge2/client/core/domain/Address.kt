package br.com.challenge2.client.core.domain

import jakarta.persistence.*

@Entity
@Table(name = "address")
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "street", nullable = false)
    var street: String,

    @Column(name = "city", nullable = false)
    var city: String,

    @Column(name = "state", nullable = false)
    var state: String,

    @Column(name = "zipcode", nullable = false)
    var zipcode: String,

    @Column(name = "country", nullable = false)
    var country: String,

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    var client: Client? = null
)