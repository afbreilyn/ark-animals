package com.afb.arkanimals.board

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito
import org.mockito.Mockito.reset
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
import java.util.*

@RunWith(SpringRunner::class)
@WebMvcTest(BoardController::class)
class BoardControllerTest {
    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var boardService: BoardService

    @Test
    fun `returns a board by id`() {
        val board = Board(title = "board title", id = 42L, stickyNotes = emptyList())
        val optionalBoard: Optional<Board> = Optional.of(board)

        given(boardService.findById(42L))
            .willReturn(optionalBoard)

        mvc.perform(
            get("/api/boards/42")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("title").value("board title"))
            .andExpect(jsonPath("id").value(42L))
            .andExpect(jsonPath("stickyNotes").isEmpty)

        reset(boardService)
    }

    @Test
    fun `given valid params creates a board`() {
        val board = Board("wooden", stickyNotes = emptyList())

        given(boardService.save(myAny(Board::class.java)))
            .willReturn(board)

        mvc.perform(post("/api/boards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(board))
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("wooden"))

        verify(boardService, VerificationModeFactory.times(1)).save(myAny(Board::class.java))
        reset(boardService)
    }

    fun toJson(`object`: Any?): ByteArray {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return mapper.writeValueAsBytes(`object`)
    }

    private fun <T> myAny(type: Class<T>): T = Mockito.any<T>(type)
}
