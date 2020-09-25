package com.afb.arkanimals

import org.junit.Before
import org.junit.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class StickyNoteControllerTest {
    lateinit var mockMvc: MockMvc
    lateinit var stubStickyNoteService: StubStickyNoteService

    @Before
    fun setUp() {
        stubStickyNoteService = StubStickyNoteService()
        mockMvc = MockMvcBuilders.standaloneSetup(StickyNoteController(stubStickyNoteService)).build()
    }

    @Test
    fun `should return a list of sticky notes`() {

        stubStickyNoteService.setReturn_getStickyNotes = listOf(
                StickyNote(
                        colour = "green",
                        text = "this is a text"
                )
        )

        mockMvc.perform(
                MockMvcRequestBuilders.get("/sticky-notes")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].colour").value("green"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].text").value("this is a text"))
    }

    @Test
    fun `should return a different list of stickynotes`() {

        stubStickyNoteService.setReturn_getStickyNotes = listOf(
                StickyNote(
                        colour = "clear",
                        text = "clear is a colour. deal with it."
                )
        )

        mockMvc.perform(
                MockMvcRequestBuilders.get("/sticky-notes")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].colour").value("clear"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].text").value("clear is a colour. deal with it."))
    }
}