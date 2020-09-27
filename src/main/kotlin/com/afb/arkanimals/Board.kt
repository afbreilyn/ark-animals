package com.afb.arkanimals

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Board(
        var name: String,
        @Id @GeneratedValue var id: Long? = null
)