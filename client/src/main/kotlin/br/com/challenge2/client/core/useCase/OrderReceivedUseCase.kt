package br.com.challenge2.client.core.useCase

import br.com.challenge2.client.application.dto.OrderReceivedDto

interface OrderReceivedUseCase {
    fun orderReceived(dto : OrderReceivedDto)
}