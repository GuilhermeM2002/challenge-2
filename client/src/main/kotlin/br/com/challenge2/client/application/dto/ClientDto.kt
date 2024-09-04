package br.com.challenge2.client.application.dto

import br.com.challenge2.client.core.domain.Address

class ClientDto(
    val id : Long? = null,
    var name : String,
    var email : String,
    var address: Address
)