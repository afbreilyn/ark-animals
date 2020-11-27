package com.afb.arkanimals.board

import com.afb.arkanimals.stickynote.StickyNote
import javax.persistence.*

@Entity
class Board(
    @Column(unique=true) var title: String,
    @OneToMany val stickyNotes: List<StickyNote> = emptyList(),
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null
)
