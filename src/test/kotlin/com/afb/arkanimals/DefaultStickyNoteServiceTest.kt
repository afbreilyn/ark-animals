package com.afb.arkanimals

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class DefaultStickyNoteServiceTest {
    lateinit var stubStickyNoteRepository: StubStickyNoteRepository
    lateinit var stickyNoteService: StickyNoteService

    @Before
    fun setUp() {
        stubStickyNoteRepository = StubStickyNoteRepository()
        stickyNoteService = DefaultStickyNoteService(stubStickyNoteRepository)
    }

    @Test
    fun `should return a list of stickynotes`() {
        stubStickyNoteRepository.setReturn_getStickyNotes = listOf(
                StickyNote(
                        colour = "blue",
                        text = "sample text"
                )
        )

        val actualStickyNotes = stickyNoteService.getStickyNotes()

        val expectStickyNotes = listOf(
                StickyNote(
                        colour = "blue",
                        text = "sample text"
                )
        )

        assertThat(actualStickyNotes).isEqualTo(expectStickyNotes)
    }

    @Test
    fun `should return a different list of stickynotes`() {
        stubStickyNoteRepository.setReturn_getStickyNotes = listOf(
                StickyNote(
                        colour = "red",
                        text = "more differenter text"
                )
        )

        val actualStickyNotes = stickyNoteService.getStickyNotes()

        val expectStickyNotes = listOf(
                StickyNote(
                        colour = "red",
                        text = "more differenter text"
                )
        )

        assertThat(actualStickyNotes).isEqualTo(expectStickyNotes)
    }
}