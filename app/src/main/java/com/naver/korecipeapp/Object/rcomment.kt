package com.naver.korecipeapp.Object

import java.io.Serializable
import java.util.*

class rcomment(
    var recipe_code: String,
    var userid: String,
    var insert_date: Date,
    var star: Float,
    var chat: String
) :Serializable{
    companion object {
        private val serialVersionUID: Long = 1L
    }
}