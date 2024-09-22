package br.com.challenge2.client.application.dto

import br.com.challenge2.client.core.domain.Client

class AddressDto(
    var id: Long?,
    var street: String,
    var city: String,
    var state: String,
    var zipcode: String,
    var country: String,
    var client: Client?
) {
    constructor() : this(null, "", "", "", "", "", null)
}