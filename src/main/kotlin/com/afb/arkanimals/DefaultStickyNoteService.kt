package com.afb.arkanimals

import org.springframework.stereotype.Service

@Service
class DefaultStickyNoteService(val stickyNoteRepository: StickyNoteRepository): StickyNoteService {
    override fun getStickyNotes(): List<StickyNote> {
        return stickyNoteRepository.getStickyNotes()
    }
}