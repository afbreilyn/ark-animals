package com.afb.arkanimals

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class StickyNoteRepositoryTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var stickyNoteRepository: StickyNoteRepository

    @Test
    fun `returns a Mustachio when the id exists`() {
        val stickyNote = StickyNote("meow")
        entityManager.persist(stickyNote)
        entityManager.flush()
        val found = stickyNoteRepository.findByIdOrNull(stickyNote.id!!)
        assertThat(found).isEqualTo(stickyNote)
    }

    @Test
    fun `returns a sticky note when searching by content`() {
        val stickyNote = StickyNote("meow name")
        entityManager.persist(stickyNote)
        val stickyNote2 = StickyNote("another name")
        entityManager.persist(stickyNote2)
        entityManager.flush()

        val found = stickyNoteRepository.findAllByContent("meow name")
        assertThat(found.first()).isEqualTo(stickyNote)
    }

    @Test
    fun `saves and returns a stickyNote`() {
        val stickyNote = StickyNote("bb8")
        val found = stickyNoteRepository.save(stickyNote)

        assertThat(found).isEqualTo(stickyNote)
    }
}
