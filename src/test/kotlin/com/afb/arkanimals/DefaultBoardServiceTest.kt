package com.afb.arkanimals

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class DefaultBoardServiceTest {
    lateinit var stubBoardRepository: StubBoardRepository
    lateinit var boardService: BoardService

    @Before
    fun setUp() {
        stubBoardRepository = StubBoardRepository()
        boardService = DefaultBoardService(stubBoardRepository)
    }

    @Test
    fun `should return a list of boards`() {
        stubBoardRepository.setReturn_getBoards = listOf(
                Board(
                        name = "board name"
                )
        )

        val actualBoards = boardService.getBoards()

        val expectedBoards = listOf(
                Board(
                        name = "board name"
                )
        )

        assertThat(actualBoards).isEqualTo(expectedBoards)
    }

    @Test
    fun `should return a different list of boards`() {
        stubBoardRepository.setReturn_getBoards = listOf(
                Board(
                        name = "another board name"
                )
        )

        val actualBoards = boardService.getBoards()

        val expectedBoards = listOf(
                Board(
                        name = "another board name"
                )
        )

        assertThat(actualBoards).isEqualTo(expectedBoards)
    }
}