package com.afb.arkanimals

class StubStickyNoteRepository : StickyNoteRepository {
    lateinit var setReturn_getStickyNotes: List<StickyNote>

    override fun getStickyNotes(): List<StickyNote> {
        return setReturn_getStickyNotes
    }
}
