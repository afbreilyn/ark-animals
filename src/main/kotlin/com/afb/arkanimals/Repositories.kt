package com.afb.arkanimals

import org.springframework.data.repository.CrudRepository

interface CardRepository : CrudRepository<Card, Long> {
    fun findBySlug(slug: String): Card?
    fun findAllByOrderByAddedAtDesc(): Iterable<Card>
}

interface UserRepository : CrudRepository<User, Long> {
    fun findByLogin(login: String): User?
}
