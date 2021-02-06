package com.afb.arkanimals.stickynote

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class StickyNoteRepositoryTest {
    @Autowired
    lateinit var stickyNoteRepository: StickyNoteRepository

    @Test
    fun `returns a StickyNote when the id exists`() {
        val stickyNote = StickyNote(content = "meow", boardId = 1L)
        stickyNoteRepository.save(stickyNote)

        val found = stickyNoteRepository.findByIdOrNull(stickyNote.id!!)
        assertThat(found).isEqualTo(stickyNote)
    }

    @Test
    fun `saves and returns a stickyNote`() {
        val stickyNote = StickyNote(content = "bb8", boardId = 4L)
        val found = stickyNoteRepository.save(stickyNote)

        assertThat(found).isEqualTo(stickyNote)
    }

    @Test
    fun `can delete a sticky note if one exists`() {
        val stickyNote = StickyNote(content = "bastila shan", boardId = 5L)
        val saved: StickyNote = stickyNoteRepository.save(stickyNote)

        val repoResponse = stickyNoteRepository.deleteById(saved.id!!)

        try {
            val nope = stickyNoteRepository.findByIdOrNull(1L)
            assertThat(nope).isEqualTo(null)
        } catch (error: Exception) {
        }

        assertThat(repoResponse).isEqualTo(Unit)
    }

    @Test
    fun `throws an error when told to delete a sticky note that doesn't exist`() {
        try {
            stickyNoteRepository.deleteById(66L)
        } catch (error: Exception) {
            assertThat(error.message).contains("entity with id 66 exists!")
        }
    }
}
