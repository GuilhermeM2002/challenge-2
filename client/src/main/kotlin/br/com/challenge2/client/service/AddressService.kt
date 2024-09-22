package br.com.challenge2.client.service

import br.com.challenge2.client.application.dto.AddressDto
import br.com.challenge2.client.core.domain.Address
import br.com.challenge2.client.repository.AddressRepository
import jakarta.persistence.EntityNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class AddressService {
    @Autowired
    private lateinit var addressRepository: AddressRepository
    @Autowired
    private lateinit var mapper : ModelMapper

    fun persistAddress(dto : AddressDto) : AddressDto {
        val address : Address = mapper.map(dto, Address::class.java)

        addressRepository.save(address)

        return mapper.map(address, AddressDto::class.java)
    }

    fun updateAddress(dto : AddressDto, id : Long) : AddressDto {
        val addressUpdate = addressRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Address with ID $id not found") }

        addressUpdate.updateAddress(dto)
        addressRepository.save(addressUpdate)

        return mapper.map(addressUpdate, AddressDto::class.java)
    }

    fun deleteAddress(id : Long){
        val productDelete = addressRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Address with ID $id not found") }

        addressRepository.delete(productDelete)
    }

    fun findAddressById(id : Long) : AddressDto {
        val product : Address = addressRepository.findById(id)
            .orElseThrow{ EntityNotFoundException("Address with ID $id not found") }

        return mapper.map(product, AddressDto::class.java)
    }

    fun findAllAddress (pageable: Pageable) : Page<AddressDto> {
        val addresses = addressRepository.findAll(pageable).map{
                address -> mapper.map(address, AddressDto::class.java)}

        return addresses
    }
}