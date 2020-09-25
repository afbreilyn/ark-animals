package com.afb.arkanimals

class StubStickyNoteService: StickyNoteService {
    lateinit var setReturn_getStickyNotes: List<StickyNote>

    override fun getStickyNotes(): List<StickyNote> {
        return setReturn_getStickyNotes
    }
}