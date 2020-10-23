package com.afb.arkanimals

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class MustachioController {

    @Autowired
    lateinit var mustachioService: MustachioService

    @GetMapping("/mustachios")
    fun getAllMustachios(): List<Mustachio> {
        return mustachioService.findAll().toList()
    }

    @GetMapping("/mustachios/{name}")
    fun getAllMustachiosByName(@PathVariable name: String): List<Mustachio> {
        return mustachioService.findAllByFirstName(name).toList()
    }

    @PostMapping("/mustachios")
    fun createMustachio(@RequestBody mustachio: Mustachio): ResponseEntity<Mustachio> {
        val status = HttpStatus.CREATED
        val saved: Mustachio = mustachioService.save(mustachio)
        return ResponseEntity(saved, status)
    }
}
