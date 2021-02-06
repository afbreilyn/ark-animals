package com.afb.arkanimals.stickynote

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class DefaultStickyNoteService : StickyNoteService {
    @Autowired
    lateinit var stickyNoteRepository: StickyNoteRepository

    override fun findAll(): Iterable<StickyNote> {
        return stickyNoteRepository.findAll()
    }

    override fun save(stickyNote: StickyNote): StickyNote {
        return stickyNoteRepository.save(stickyNote)
    }

    override fun delete(id: Long): StickyNoteServiceResponse {
        var status: Boolean
        try {
            stickyNoteRepository.deleteById(id)
            status = true
        } catch(error: Exception) {
            status = false
        }

        return StickyNoteServiceResponse(
            stickyNoteId = id,
            stickyNote = null,
            status = status
        )
    }
}
