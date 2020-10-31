package com.afb.arkanimals

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultStickyNoteService : StickyNoteService {
    @Autowired
    lateinit var stickyNoteRepository: StickyNoteRepository

    override fun findAllByContent(content: String): Iterable<StickyNote> {
        return stickyNoteRepository.findAllByContent(content)
    }

    override fun findAll(): Iterable<StickyNote> {
        return stickyNoteRepository.findAll()
    }

    override fun save(stickyNote: StickyNote): StickyNote {
        return stickyNoteRepository.save(stickyNote)
    }
}
