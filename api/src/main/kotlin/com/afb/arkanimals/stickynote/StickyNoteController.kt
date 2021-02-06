package com.afb.arkanimals.stickynote

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

    @PostMapping("/sticky-notes")
    fun createStickyNote(@RequestBody stickyNote: StickyNote): ResponseEntity<StickyNote> {
        val status = HttpStatus.CREATED
        val saved: StickyNote = stickyNoteService.save(stickyNote)
        return ResponseEntity(saved, status)
    }

    @DeleteMapping("/sticky-notes/{id}")
    fun deleteStickyNote(@PathVariable id: Long): ResponseEntity<Void> {
        val didItWork: StickyNoteServiceResponse = stickyNoteService.delete(id)

        val status: HttpStatus = if (didItWork.status) {
            HttpStatus.NO_CONTENT
        } else {
            HttpStatus.NOT_FOUND
        }

        return ResponseEntity(status)
    }
}
