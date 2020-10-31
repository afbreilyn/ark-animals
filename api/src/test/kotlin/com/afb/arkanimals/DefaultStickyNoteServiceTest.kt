package com.afb.arkanimals

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
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
        fun mustachioService(): StickyNoteService {
            return DefaultStickyNoteService()
        }
    }

    @MockBean
    lateinit var stickyNoteRepository: StickyNoteRepository

    @Autowired
    lateinit var stickyNoteService: StickyNoteService

    @Before
    fun setUp() {
        var tjStickyNote = StickyNote("TJ", 2L)
        var allStickyNotes = arrayOf(tjStickyNote).toList()

        Mockito.`when`(stickyNoteRepository.findAllByContent("TJ"))
                .thenReturn(allStickyNotes)

        Mockito.`when`(stickyNoteRepository.findAll())
                .thenReturn(allStickyNotes)
    }

    @Test
    fun `it getsAllByContent`() {
        val content = "TJ"
        val found = stickyNoteService.findAllByContent(content)

        assertThat(found.first().content).isEqualTo(content)
    }

    @Test
    fun `finds all`() {
        val found = stickyNoteRepository.findAll()

        assertThat(found.first().content).isEqualTo("TJ")
    }

    @Test
    fun `saves`() {
        val shouldBeSavedAndReturned = StickyNote("luke")

        Mockito.`when`(stickyNoteRepository.save(shouldBeSavedAndReturned))
                .thenReturn(shouldBeSavedAndReturned)

        val returnedStickyNote: StickyNote = stickyNoteRepository.save(shouldBeSavedAndReturned)

        assertThat(returnedStickyNote).isEqualTo(shouldBeSavedAndReturned)
    }
}