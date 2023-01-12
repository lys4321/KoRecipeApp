package com.naver.korecipeapp.Object

import java.io.Serializable

class cooking(
    var recipe_code: String,
    var num: Int,
    var c_order: String,
    var image_url: String?
):Serializable{
    companion object {
        private val serialVersionUID: Long = 1L
    }
}
