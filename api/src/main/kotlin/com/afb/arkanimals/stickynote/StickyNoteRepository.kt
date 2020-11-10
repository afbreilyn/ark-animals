package com.afb.arkanimals.stickynote

import org.springframework.data.jpa.repository.JpaRepository

interface StickyNoteRepository : JpaRepository<StickyNote, Long> {
    fun findAllByContent(content: String): Iterable<StickyNote>
}
