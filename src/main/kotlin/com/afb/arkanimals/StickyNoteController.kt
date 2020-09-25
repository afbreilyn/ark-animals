package com.afb.arkanimals

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StickyNoteController(val stickyNoteService: StickyNoteService) {

    @GetMapping("/sticky-notes")
    fun getStickyNotes(): List<StickyNote> {
        return stickyNoteService.getStickyNotes()
    }
}
