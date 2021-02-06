package com.afb.arkanimals.stickynote

import org.springframework.data.jpa.repository.JpaRepository

interface StickyNoteRepository : JpaRepository<StickyNote, Long> {
    fun findAllByBoardId(boardId: Long): Iterable<StickyNote>
}
