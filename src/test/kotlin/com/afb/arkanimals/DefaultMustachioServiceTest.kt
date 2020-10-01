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
class DefaultMustachioServiceTest {
    @TestConfiguration
    class DefaultMustachioServiceTestContextConfiguration {
        @Bean
        fun mustachioService(): MustachioService {
            return DefaultMustachioService()
        }
    }

    @MockBean
    lateinit var mustachioRepository: MustachioRepository

    @Autowired
    lateinit var mustachioService: MustachioService

    @Before
    fun setUp() {
        var tjMustachio = Mustachio("TJ", 2L)
        var allMustachios = arrayOf(tjMustachio).toList()

        Mockito.`when`(mustachioRepository.findAllByFirstname("TJ"))
                .thenReturn(allMustachios)

        Mockito.`when`(mustachioRepository.findAll())
                .thenReturn(allMustachios)
    }

    @Test
    fun `it getsAllByFirstname`() {
        val firstname = "TJ"
        val found = mustachioService.findAllByFirstname(firstname)

        assertThat(found.first().firstname).isEqualTo(firstname)
    }

    @Test
    fun `finds all`() {
        val found = mustachioRepository.findAll()

        assertThat(found.first().firstname).isEqualTo("TJ")
    }

    @Test
    fun `saves`() {
        val shouldBeSavedAndReturned = Mustachio("luke")

        Mockito.`when`(mustachioRepository.save(shouldBeSavedAndReturned))
                .thenReturn(shouldBeSavedAndReturned)

        val returnedMustachio: Mustachio = mustachioRepository.save(shouldBeSavedAndReturned)

        assertThat(returnedMustachio).isEqualTo(shouldBeSavedAndReturned)
    }
}
