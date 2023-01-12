package com.naver.korecipeapp.Object

import java.io.Serializable

data class base (
    var recipe_code: String,
    var recipe_name: String,
    var recipe_summary: String,
    var userid: String,
    var recipe_kind: String,
    var cooking_time: String,
    var view_count: Int,
    var image_url: String,
    var check_video: Boolean
):Serializable{
    companion object {
        private val serialVersionUID: Long = 1L
    }
}