package com.afb.arkanimals.stickynote

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.mockito.internal.verification.VerificationModeFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(StickyNoteController::class)
class StickyNoteControllerTest {
    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var stickyNoteService: StickyNoteService

    @Test
    fun `returns a list of sticky notes by name`() {
        val stickyNote = StickyNote("walterino", 1L)
        val allStickyNotes = arrayOf(stickyNote).asList()
        given(stickyNoteService.findAllByContent("walterino"))
                .willReturn(allStickyNotes)

        mvc.perform(
                get("/api/sticky-notes/walterino")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[*].content").value("walterino"))
                .andExpect(jsonPath("$[*].boardId").value(1))
    }

    @Test
    fun `returns all sticky notes`() {
        val stickyNote1 = StickyNote("walterino", 1L)
        val stickyNote2 = StickyNote("anne", 2L)
        val allStickyNotes = arrayOf(stickyNote1, stickyNote2).asList()

        given(stickyNoteService.findAll())
                .willReturn(allStickyNotes)

        mvc.perform(
                get("/api/sticky-notes")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].content").value("walterino"))
                .andExpect(jsonPath("$[0].boardId").value(1))
                .andExpect(jsonPath("$[1].content").value("anne"))
                .andExpect(jsonPath("$[1].boardId").value(2))

        reset(stickyNoteService);
    }

    @Test
    fun `given valid params creates a sticky note`() {
        val stickyNote = StickyNote("margo", 3L)

        given(stickyNoteService.save(myAny(StickyNote::class.java)))
                .willReturn(stickyNote)

        mvc.perform(post("/api/sticky-notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(stickyNote))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("margo"))
                .andExpect(jsonPath("$.boardId").value(3))

        verify(stickyNoteService, VerificationModeFactory.times(1)).save(myAny(StickyNote::class.java))
        reset(stickyNoteService)
    }

    fun toJson(`object`: Any?): ByteArray {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return mapper.writeValueAsBytes(`object`)
    }

    private fun <T> myAny(type: Class<T>): T = Mockito.any<T>(type)
}
