package com.afb.arkanimals.stickynote

interface StickyNoteService {
    fun findAllByContent(content: String): Iterable<StickyNote>
    fun findAll(): Iterable<StickyNote>
    fun save(stickyNote: StickyNote): StickyNote
}
