package br.com.challenge2.client.application.dto

import br.com.challenge2.client.core.domain.Address

class ClientDto(
    var id : Long?,
    var name : String,
    var email : String,
    var addresses: List<Address> = emptyList()
){
    constructor() : this(null, "", "", mutableListOf())
}