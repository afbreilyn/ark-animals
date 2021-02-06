package com.afb.arkanimals.stickynote

data class StickyNoteServiceResponse(
    var stickyNoteId: Long,
    var status: Boolean,
    var stickyNote: StickyNote? = null
)
