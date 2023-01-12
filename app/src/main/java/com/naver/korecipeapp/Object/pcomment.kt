package com.naver.korecipeapp.Object

import java.io.Serializable
import java.util.*

class pcomment(
    var post_code:String,
    var userid:String,
    var chat_date: Date,
    var chat:String
): Serializable {
    companion object {
        private val serialVersionUID: Long = 1L
    }
}