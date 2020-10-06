package com.afb.arkanimals

import org.springframework.data.jpa.repository.JpaRepository

interface MustachioRepository : JpaRepository<Mustachio, Long> {
    fun findAllByFirstname(firstName: String): Iterable<Mustachio>
}
