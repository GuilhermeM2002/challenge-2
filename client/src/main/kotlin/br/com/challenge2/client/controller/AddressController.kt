package br.com.challenge2.client.controller

import br.com.challenge2.client.application.dto.AddressDto
import br.com.challenge2.client.service.AddressService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/address")
class AddressController {
    @Autowired
    private lateinit var service : AddressService

    @PostMapping
    @Transactional
    fun registerAddress(@RequestBody dto : AddressDto, builder : UriComponentsBuilder) : ResponseEntity<AddressDto> {
        val uri = builder.path("/address/{id}").buildAndExpand(dto.id).toUri()
        val address = service.persistAddress(dto)

        return ResponseEntity.created(uri).body(address)
    }

    @PutMapping("/{id}")
    @Transactional
    fun editAddress(@RequestBody dto : AddressDto, @PathVariable id : Long) : ResponseEntity<AddressDto> {
        val address : AddressDto = service.updateAddress(dto, id)

        return ResponseEntity.ok(address)
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun removeAddress(@PathVariable id : Long) : ResponseEntity<Void> {
        service.deleteAddress(id)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun findOneAddress(@PathVariable id : Long) : ResponseEntity<AddressDto> {
        val address : AddressDto = service.findAddressById(id)

        return ResponseEntity.ok(address)
    }

    @GetMapping
    fun findAllAddress(pageable: Pageable) : ResponseEntity<Page<AddressDto>> {
        val allAddress = service.findAllAddress(pageable)

        return ResponseEntity.ok(allAddress)
    }
}