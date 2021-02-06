package com.afb.arkanimals.stickynote

interface StickyNoteService {
    fun findAll(): Iterable<StickyNote>
    fun save(stickyNote: StickyNote): StickyNote
    fun delete(id: Long): StickyNoteServiceResponse
}
