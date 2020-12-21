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
    fun `returns a sticky note when searching by content`() {
        val stickyNote = StickyNote(content = "meow name", boardId = 2L)
        val stickyNote2 = StickyNote(content = "another name", boardId = 3L)
        stickyNoteRepository.save(stickyNote)
        stickyNoteRepository.save(stickyNote2)

        val found = stickyNoteRepository.findAllByContent("meow name")
        assertThat(found.first()).isEqualTo(stickyNote)
    }

    @Test
    fun `saves and returns a stickyNote`() {
        val stickyNote = StickyNote(content = "bb8", boardId = 4L)
        val found = stickyNoteRepository.save(stickyNote)

        assertThat(found).isEqualTo(stickyNote)
    }
}
