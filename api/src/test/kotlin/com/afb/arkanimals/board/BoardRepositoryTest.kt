package com.afb.arkanimals.board

import com.afb.arkanimals.stickynote.StickyNote
import com.afb.arkanimals.stickynote.StickyNoteRepository
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@DataJpaTest
class BoardRepositoryTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Test
    fun `returns an empty Board by id`() {
        val board = Board("who cares", stickyNotes=emptyList())
        entityManager.persist(board)
        entityManager.flush()

        val foundBoard: Board? = boardRepository.findByIdOrNull(board.id!!)
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
        val board = Board("bored", stickyNotes=emptyList())
        val foundBoard = boardRepository.save(board)

        assertThat(foundBoard).isEqualTo(board)
    }
}