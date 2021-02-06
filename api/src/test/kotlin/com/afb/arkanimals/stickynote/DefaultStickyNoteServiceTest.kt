package com.afb.arkanimals.stickynote

import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class DefaultStickyNoteServiceTest {
    @TestConfiguration
    class DefaultStickyNoteServiceTestContextConfiguration {
        @Bean
        fun returnDefaultStickyNoteService(): StickyNoteService {
            return DefaultStickyNoteService()
        }
    }

    @MockBean
    lateinit var stickyNoteRepository: StickyNoteRepository

    @Autowired
    lateinit var stickyNoteService: StickyNoteService

    @Before
    fun setUp() {
        val tjStickyNote = StickyNote(content = "TJ", id = 2L, boardId = 1L)
        val allStickyNotes = arrayOf(tjStickyNote).toList()

        Mockito.`when`(stickyNoteRepository.findAll())
                .thenReturn(allStickyNotes)
    }

    @Test
    fun `finds all`() {
        val found = stickyNoteRepository.findAll()

        assertThat(found.first().content).isEqualTo("TJ")
    }

    @Test
    fun `saves and return a stickynote`() {
        val shouldBeSavedAndReturned = StickyNote(content = "luke", boardId = 1L)

        Mockito.`when`(stickyNoteRepository.save(shouldBeSavedAndReturned))
                .thenReturn(shouldBeSavedAndReturned)

        val returnedStickyNote: StickyNote = stickyNoteService.save(shouldBeSavedAndReturned)

        assertThat(returnedStickyNote).isEqualTo(shouldBeSavedAndReturned)
    }

    @Test
    fun `deletes a sticky when there is one to delete`() {
        doNothing().`when`(stickyNoteRepository).deleteById(2L)

        val found = stickyNoteRepository.findAll()
        assertThat(found.first().id).isEqualTo(2L)

        val response: StickyNoteServiceResponse = stickyNoteService.delete(2L)

        assertThat(response.stickyNoteId).isEqualTo(2L)
        assertThat(response.stickyNote).isNull()
        assertThat(response.status).isEqualTo(true)
    }

    @Test
    fun `returns a failure when told to delete a stickynote that doesn't exist`() {
        doThrow(RuntimeException("this is an exception")).`when`(stickyNoteRepository).deleteById(5L)

        val response: StickyNoteServiceResponse = stickyNoteService.delete(5L)

        assertThat(response.stickyNoteId).isEqualTo(5L)
        assertThat(response.stickyNote).isNull()
        assertThat(response.status).isEqualTo(false)
    }
}
