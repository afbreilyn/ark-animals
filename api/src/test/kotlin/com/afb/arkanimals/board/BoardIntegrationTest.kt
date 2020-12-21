package com.afb.arkanimals.board

import com.afb.arkanimals.ArkAnimalsApplication
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [ArkAnimalsApplication::class])
@AutoConfigureMockMvc
class BoardIntegrationTest {
    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var boardRepository: BoardRepository

    @After
    fun resetDb() {
        boardRepository.deleteAll()
    }

    @Test
    fun `fetches a board with no sticky notes`() {
        val myCoolBoard = BoardEntity(
            title = "a very cool board"
        )

        boardRepository.saveAndFlush(myCoolBoard)

        // NB: the generated value of the id isn't overrideable, thus we can hardcode it to be '2'
        mvc.perform(
            get("/api/boards/2")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.jsonPath("title").value("a very cool board"))
    }

    @Test
    fun `saves and returns a board`() {
        val mustachio = Board("pinewood", stickyNotes = emptyList())

        mvc.perform(MockMvcRequestBuilders.post("/api/boards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(mustachio)))

        val found: List<BoardEntity> = boardRepository.findAll()
        assertThat(found.first().title).isEqualTo("pinewood")
        assertThat(found.size).isEqualTo(1)
    }

    fun toJson(`object`: Any?): ByteArray {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return mapper.writeValueAsBytes(`object`)
    }
}