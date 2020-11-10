package com.afb.arkanimals.board

import javax.persistence.*

@Entity
class Board(
    @Column(unique=true) var title: String,

    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null
)
