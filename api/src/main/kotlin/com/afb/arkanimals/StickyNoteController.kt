package com.afb.arkanimals

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class StickyNoteController {

    @Autowired
    lateinit var stickyNoteService: StickyNoteService

    @GetMapping("/sticky-notes")
    fun getAllStickyNotes(): List<StickyNote> {
        return stickyNoteService.findAll().toList()
    }

    @GetMapping("/sticky-notes/{content}")
    fun getAllStickyNotesByContent(@PathVariable content: String): List<StickyNote> {
        return stickyNoteService.findAllByContent(content).toList()
    }

    @PostMapping("/sticky-notes")
    fun createStickyNote(@RequestBody stickyNote: StickyNote): ResponseEntity<StickyNote> {
        val status = HttpStatus.CREATED
        val saved: StickyNote = stickyNoteService.save(stickyNote)
        return ResponseEntity(saved, status)
    }
}
