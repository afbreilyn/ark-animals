package com.afb.arkanimals

interface StickyNoteRepository {
    fun getStickyNotes(): List<StickyNote>
}