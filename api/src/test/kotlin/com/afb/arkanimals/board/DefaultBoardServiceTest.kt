package com.afb.arkanimals.board

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
import java.util.*

@RunWith(SpringRunner::class)
class DefaultBoardServiceTest {
    @TestConfiguration
    class DefaultBoardServiceTestContextConfiguration {
        @Bean
        fun returnDefaultBoardService(): BoardService {
            return DefaultBoardService()
        }
    }

    @MockBean
    lateinit var boardRepository: BoardRepository

    @Autowired
    lateinit var boardService: BoardService

    @Before
    fun setUp() {
        val datBoard: Board = Board("random title", id = 3L)

        val datOptional: Optional<Board> = Optional.of(datBoard)
        Mockito.`when`(boardRepository.findById(3L))
            .thenReturn(datOptional)
    }

    @Test
    fun `it returnsById`() {
        val foundBoard = boardService.findById(3L)

        assertThat(foundBoard.get().title).isEqualTo("random title")
    }

    @Test
    fun `saves`() {
        val shouldBeSavedAndReturned = Board("pls")

        Mockito.`when`(boardRepository.save(shouldBeSavedAndReturned))
            .thenReturn(shouldBeSavedAndReturned)

        val returnedStickyNote: Board = boardService.save(shouldBeSavedAndReturned)

        assertThat(returnedStickyNote).isEqualTo(shouldBeSavedAndReturned)
    }
}