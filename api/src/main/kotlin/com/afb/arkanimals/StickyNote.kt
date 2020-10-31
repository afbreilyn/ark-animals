package com.afb.arkanimals

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class StickyNote(
    var content: String,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null
)
