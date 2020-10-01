package com.afb.arkanimals

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

//@Table(name = "mustachio")
@Entity
class Mustachio(
    var firstname: String,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null
)
