package com.afb.arkanimals

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class MustachioRepositoryTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var mustachioRepository: MustachioRepository

    @Test
    fun `returns a Mustachio when the id exists`() {
        val mustachio = Mustachio("meow")
        entityManager.persist(mustachio)
        entityManager.flush()
        val found = mustachioRepository.findByIdOrNull(mustachio.id!!)
        assertThat(found).isEqualTo(mustachio)
    }

    @Test
    fun `returns a Mustachio when searching by firstname`() {
        val mustachio = Mustachio("meow name")
        entityManager.persist(mustachio)
        val mustachio2 = Mustachio("another name")
        entityManager.persist(mustachio2)
        entityManager.flush()

        val found = mustachioRepository.findAllByFirstname("meow name")
        assertThat(found.first()).isEqualTo(mustachio)
    }

    @Test
    fun `saves and returns a mustachio`() {
        val mustachio = Mustachio("bb8")
        val found = mustachioRepository.save(mustachio)

        assertThat(found).isEqualTo(mustachio)
    }
}
