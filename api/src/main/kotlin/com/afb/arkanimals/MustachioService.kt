package com.afb.arkanimals

interface MustachioService {
    fun findAllByFirstname(firstName: String): Iterable<Mustachio>
    fun findAll(): Iterable<Mustachio>
    fun save(mustachio: Mustachio): Mustachio
}
