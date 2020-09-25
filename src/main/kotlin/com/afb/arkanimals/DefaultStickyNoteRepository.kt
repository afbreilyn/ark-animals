package com.afb.arkanimals

import org.springframework.stereotype.Repository

@Repository
class DefaultStickyNoteRepository : StickyNoteRepository {
    override fun getStickyNotes(): List<StickyNote> {
        return listOf(
                StickyNote(
                        colour = "red",
                        text = "this is text"
                )
        )
    }
}