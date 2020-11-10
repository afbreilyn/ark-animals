package com.afb.arkanimals.stickynote

import com.afb.arkanimals.board.Board
import javax.persistence.*

@Entity
class StickyNote(
    var content: String,
//    @ManyToOne var board: Board,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null
)
