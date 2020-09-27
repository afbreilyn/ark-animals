package com.afb.arkanimals

interface BoardRepository {
    fun getBoards(): List<Board>
    fun getBoardById(id: String): Board
}