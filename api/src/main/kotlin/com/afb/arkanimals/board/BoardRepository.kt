package com.afb.arkanimals.board

import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<BoardEntity, Long> {
    fun findByTitle(title: String): BoardEntity
}
