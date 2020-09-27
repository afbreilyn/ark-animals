package com.afb.arkanimals

interface BoardService {
    fun getBoards(): List<Board>
    fun getBoardById(id: String): Board
}