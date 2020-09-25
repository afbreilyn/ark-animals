package com.afb.arkanimals

interface BoardRepository {
    fun getBoards(): List<Board>
}