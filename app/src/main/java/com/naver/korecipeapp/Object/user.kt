package com.naver.korecipeapp.Object

import java.io.Serializable

class user(
    var userid: String,
    var userpw: String,
    var user_name: String,
    var user_pnum: String,
    var recipe_count: Int
):Serializable{
    companion object {
        private val serialVersionUID: Long = 1L
    }
}