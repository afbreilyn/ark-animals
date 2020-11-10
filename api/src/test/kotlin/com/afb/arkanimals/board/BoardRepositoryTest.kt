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

@DataJpaTest
class BoardRepositoryTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Test
    fun `returns an empty Board by id with no title`() {
        val board = Board("")
        entityManager.persist(board)
        entityManager.flush()
        val foundBoard  = boardRepository.findByIdOrNull(board.id!!)
        assertThat(foundBoard).isEqualTo(board)
    }

    @Test
    fun `returns an empty Board by its title`() {
        val board = Board("title")
        entityManager.persist(board)
        entityManager.flush()
        val foundBoard  = boardRepository.findByTitle("title")
        assertThat(foundBoard).isEqualTo(board)
    }

    @Test
    fun `saves and returns a board`() {
        val board = Board("bored")
        val foundBoard = boardRepository.save(board)

        assertThat(foundBoard).isEqualTo(board)
    }

    @Test
    fun `returns a Board with StickyNotes`() {
//        TODO: this thing
//        val stickyNote:
//        val board = Board(title="meow", )
    }

//    this should be in the controller which will catch the weirdo database error from
//    @Test
//    fun `throws an error when trying to save a board with a duplicate title`() {
//    }
}