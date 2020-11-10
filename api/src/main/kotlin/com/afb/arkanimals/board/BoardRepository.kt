package com.afb.arkanimals.board

import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {
    fun findByTitle(title: String): Board
}