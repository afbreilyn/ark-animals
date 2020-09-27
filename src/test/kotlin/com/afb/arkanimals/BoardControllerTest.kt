package com.afb.arkanimals

import org.junit.Before
import org.junit.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class BoardControllerTest {
    lateinit var mockMvc: MockMvc
    lateinit var stubBoardService: StubBoardService

    @Before
    fun setUp() {
        stubBoardService = StubBoardService()
        mockMvc = MockMvcBuilders.standaloneSetup(BoardController(stubBoardService)).build()
    }

    @Test
    fun `returns a list of boards`() {
        stubBoardService.setReturn_getBoards = listOf(
                Board( name = "my cool board" )
        )

        mockMvc.perform(
                MockMvcRequestBuilders.get("/boards")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").value("my cool board"))
    }

    @Test
    fun `returns a different list of boards`() {
        stubBoardService.setReturn_getBoards = listOf(
                Board( name = "another even cooler board" )
        )

        mockMvc.perform(
                MockMvcRequestBuilders.get("/boards")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").value("another even cooler board"))
    }

    @Test
    fun `returns a specific board when passed an id`() {
        var board = Board(
                name = "a specific board",
                id = 7.toLong()
        )

        stubBoardService.setReturn_getBoardById = board

        mockMvc.perform(
                MockMvcRequestBuilders.get("/boards/${board.id}")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("a specific board"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("${board.id}"))


    }
}
