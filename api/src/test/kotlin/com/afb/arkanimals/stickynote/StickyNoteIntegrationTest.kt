package com.afb.arkanimals.stickynote

import com.afb.arkanimals.ArkAnimalsApplication
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [ArkAnimalsApplication::class])
@AutoConfigureMockMvc
//@EnableAutoConfiguration(exclude = [SecurityAutoConfiguration::class])
//@AutoConfigureTestDatabase
class StickyNoteIntegrationTest {
    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var stickyNoteRepository: StickyNoteRepository

    @After
    fun resetDb() {
        stickyNoteRepository.deleteAll()
    }

    @Test
    fun `returns from all`() {
        val stickyNote = StickyNote("aaron")
        stickyNoteRepository.saveAndFlush(stickyNote)

        mvc.perform(
                MockMvcRequestBuilders.get("/api/sticky-notes/aaron")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].content").value("aaron"))

    }

    @Test
    fun `findAll works`() {
        val mustachio = StickyNote("cedar")
        stickyNoteRepository.saveAndFlush(mustachio)

        mvc.perform(
                MockMvcRequestBuilders.get("/api/sticky-notes")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].content").value("cedar"))
    }

    @Test
    fun `saves and returns one of the mustachios`() {
        val mustachio = StickyNote("anne")

        println(toJson(mustachio))

        mvc.perform(post("/api/sticky-notes").contentType(MediaType.APPLICATION_JSON).content(toJson(mustachio)))

        val found: List<StickyNote> = stickyNoteRepository.findAll()
        assertThat(found.first().content).isEqualTo("anne")
    }

    fun toJson(`object`: Any?): ByteArray {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return mapper.writeValueAsBytes(`object`)
    }
}
