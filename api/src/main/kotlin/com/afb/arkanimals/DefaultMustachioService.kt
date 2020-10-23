package com.afb.arkanimals

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultMustachioService : MustachioService {
    @Autowired
    lateinit var mustachioRepository: MustachioRepository

    override fun findAllByFirstName(firstName: String): Iterable<Mustachio> {
        return mustachioRepository.findAllByFirstName(firstName)
    }

    override fun findAll(): Iterable<Mustachio> {
        return mustachioRepository.findAll()
    }

    override fun save(mustachio: Mustachio): Mustachio {
        return mustachioRepository.save(mustachio)
    }
}
