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
                        name = "board name",
                        id = 7.toLong()
                )
        )

        val actualBoards = boardService.getBoards()

        val expectedBoards = listOf(
                Board(
                        name = "board name",
                        id = 7.toLong()
                )
        )

        assertThat(actualBoards[0].name).isEqualTo(expectedBoards[0].name)
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

        assertThat(actualBoards[0].name).isEqualTo(expectedBoards[0].name)
    }

    @Test
    fun `returns a specific board when passed an id`() {
        val specificBoard = Board(
                name = "specific board",
                id = 123.toLong()
        )

        stubBoardRepository.setReturn_getBoardById = specificBoard

        val actualBoard = boardService.getBoardById("${specificBoard.id}")

        val expectedBoard =
                Board(
                        name = "specific board",
                        id = 123.toLong()
                )


        assertThat(actualBoard.name).isEqualTo(expectedBoard.name)
        assertThat(actualBoard.name).isEqualTo(expectedBoard.name)
    }
}