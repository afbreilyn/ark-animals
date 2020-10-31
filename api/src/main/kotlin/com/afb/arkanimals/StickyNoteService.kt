package com.afb.arkanimals

interface StickyNoteService {
    fun findAllByContent(content: String): Iterable<StickyNote>
    fun findAll(): Iterable<StickyNote>
    fun save(stickyNote: StickyNote): StickyNote
}
