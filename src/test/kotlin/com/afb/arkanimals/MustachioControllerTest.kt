package com.afb.arkanimals

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
@WebMvcTest(MustachioController::class)
class MustachioControllerTest {
    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var mustachioService: MustachioService

    @Test
    fun `returns a list of mustachios by name`() {
        val mustachio = Mustachio("walterino")
        val allMustachios = arrayOf(mustachio).asList()
        given(mustachioService.findAllByFirstname("walterino"))
                .willReturn(allMustachios)

        mvc.perform(
                get("/api/mustachios/walterino")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[*].firstname").value("walterino"))
    }

    @Test
    fun `returns all mustachios`() {
        val mustachio1 = Mustachio("walterino")
        val mustachio2 = Mustachio("anne")
        val allMustachios = arrayOf(mustachio1, mustachio2).asList()

        given(mustachioService.findAll())
                .willReturn(allMustachios)

        mvc.perform(
                get("/api/mustachios")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstname").value("walterino"))
                .andExpect(jsonPath("$[1].firstname").value("anne"))

        reset(mustachioService);
    }

    @Test
    fun `given valid params creates a mustachio`() {
        val mustachio = Mustachio("margo")

        given(mustachioService.save(myAny(Mustachio::class.java)))
                .willReturn(mustachio)

        mvc.perform(post("/api/mustachios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(mustachio))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname").value("margo"))

        verify(mustachioService, VerificationModeFactory.times(1)).save(myAny(Mustachio::class.java));
        reset(mustachioService);
    }

    fun toJson(`object`: Any?): ByteArray {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return mapper.writeValueAsBytes(`object`)
    }

    private fun <T> myAny(type: Class<T>): T = Mockito.any<T>(type)
}
