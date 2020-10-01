package com.afb.arkanimals

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
@EnableAutoConfiguration(exclude = [SecurityAutoConfiguration::class])
@AutoConfigureTestDatabase
class MustachioIntegrationTest {
    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var mustachioRepository: MustachioRepository

    @After
    fun resetDb() {
        mustachioRepository.deleteAll()
    }

    @Test
    fun `returns from all`() {
        val mustachio = Mustachio("aaron")
        mustachioRepository.saveAndFlush(mustachio)

        mvc.perform(
                MockMvcRequestBuilders.get("/api/mustachios/aaron")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].firstname").value("aaron"))

    }

    @Test
    fun `findAll works`() {
        val mustachio = Mustachio("cedar")
        mustachioRepository.saveAndFlush(mustachio)

        mvc.perform(
                MockMvcRequestBuilders.get("/api/mustachios")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].firstname").value("cedar"))
    }

    @Test
    fun `saves and returns on of the mustachios`() {
        val mustachio = Mustachio("anne")

        mvc.perform(post("/api/mustachios").contentType(MediaType.APPLICATION_JSON).content(toJson(mustachio)))

        val found: List<Mustachio> = mustachioRepository.findAll()
        assertThat(found.first().firstname).isEqualTo("anne")
    }

    fun toJson(`object`: Any?): ByteArray {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return mapper.writeValueAsBytes(`object`)
    }
}
