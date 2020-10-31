package com.afb.arkanimals

import org.springframework.data.jpa.repository.JpaRepository

interface StickyNoteRepository : JpaRepository<StickyNote, Long> {
    fun findAllByContent(content: String): Iterable<StickyNote>
}
