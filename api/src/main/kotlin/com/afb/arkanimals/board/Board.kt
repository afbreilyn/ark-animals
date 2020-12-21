package com.afb.arkanimals.board

import com.afb.arkanimals.stickynote.StickyNote
import javax.persistence.*

data class Board(
    var title: String,
    var stickyNotes: List<StickyNote>,
    var id: Long? = null
)

@Entity
class BoardEntity(
    @Column(unique = true) var title: String,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null
)