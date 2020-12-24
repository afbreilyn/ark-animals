package com.afb.arkanimals.board

import com.afb.arkanimals.stickynote.StickyNote
import com.afb.arkanimals.stickynote.StickyNoteRepository
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

    @MockBean
    lateinit var stickyNoteRepository: StickyNoteRepository

    @Autowired
    lateinit var boardService: BoardService

    @Test
    fun `it returnsById`() {
        val datBoardEntity = BoardEntity("random title", id = 3L)

        val datOptional: Optional<BoardEntity> = Optional.of(datBoardEntity)
        Mockito.`when`(boardRepository.findById(3L))
            .thenReturn(datOptional)

        val stickies = listOf(StickyNote("whatevs", boardId = 3L), StickyNote("yolo", boardId = 3L))
        Mockito.`when`(stickyNoteRepository.findAllByBoardId(3L))
            .thenReturn(stickies)

        val foundBoard = boardService.findById(3L)

        assertThat(foundBoard.get().title).isEqualTo("random title")
        assertThat(foundBoard.get().stickyNotes).isEqualTo(stickies)
    }

    @Test
    fun `saves`() {
        val shouldBeSaved = Board(title= "pls", stickyNotes = emptyList())
        val shouldBeReturn = BoardEntity(title = "pls", id = 1)

        Mockito.`when`(boardRepository.save(myAny(BoardEntity::class.java)))
            .thenReturn(shouldBeReturn)

        val returnedBoard: Board = boardService.save(shouldBeSaved)

        assertThat(returnedBoard).isEqualTo(Board(title= "pls", stickyNotes = emptyList(), id=1))
    }

    private fun <T> myAny(type: Class<T>): T = Mockito.any<T>(type)
}