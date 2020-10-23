package com.afb.arkanimals

interface MustachioService {
    fun findAllByFirstName(firstName: String): Iterable<Mustachio>
    fun findAll(): Iterable<Mustachio>
    fun save(mustachio: Mustachio): Mustachio
}
