package br.com.challenge2.client.controller

import br.com.challenge2.client.dto.ClientDto
import br.com.challenge2.client.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/client")
class ClientController {
    @Autowired
    lateinit var service : ClientService

    @PostMapping
    @Transactional
    fun registerClient(@RequestBody dto : ClientDto, builder : UriComponentsBuilder) : ResponseEntity<ClientDto>{
        val uri = builder.path("/client/{id}").buildAndExpand(dto.id).toUri()
        val client = service.persistClient(dto)

        return ResponseEntity.created(uri).body(client)
    }

    @PutMapping
    @Transactional
    fun editClient(@RequestBody dto : ClientDto, @RequestParam id : Long) : ResponseEntity<ClientDto>{
        val client : ClientDto = service.updateClient(dto, id)

        return ResponseEntity.ok(client)
    }

    @DeleteMapping
    @Transactional
    fun removeClient(@RequestParam id : Long) : ResponseEntity<Void>{
        service.deleteClient(id)

        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun findClient(@RequestParam email : String) : ResponseEntity<ClientDto>{
        val client : ClientDto = service.findClientByEmail(email)

        return ResponseEntity.ok(client)
    }
}