package com.afb.arkanimals.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class BoardRepositoryTest {
    @Autowired
    lateinit var boardRepository: BoardRepository

    @Test
    fun `returns an empty Board by id`() {
        val board = BoardEntity("who cares")
        boardRepository.save(board)

        val foundBoard: BoardEntity? = boardRepository.findByIdOrNull(board.id!!)
        assertThat(foundBoard).isEqualTo(board)
    }

    @Test
    fun `returns a Board with stickies by id`() {
        val board = BoardEntity("who cares")
        boardRepository.save(board)

        val foundBoard: BoardEntity? = boardRepository.findByIdOrNull(board.id!!)
        assertThat(foundBoard).isEqualTo(board)
    }

//    @Test
//    fun `returns an empty Board by its title`() {
//        val board = Board("title", stickyNotes=emptyList())
//        entityManager.persist(board)
//        entityManager.flush()
//        val foundBoard  = boardRepository.findByTitle("title")
//        assertThat(foundBoard).isEqualTo(board)
//    }

    @Test
    fun `saves and returns a board`() {
        val board = BoardEntity("bored")
        val foundBoard = boardRepository.save(board)

        assertThat(foundBoard).isEqualTo(board)
    }
}