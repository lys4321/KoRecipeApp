package com.naver.korecipeapp.Object

import java.io.Serializable
import java.util.*

class post(
    var post_code:String,
    var title:String,
    var userid:String,
    var post_date: Date,
    var post_in:String,
    var view_count:Int,
    var check_image:Boolean,
    var check_video:Boolean
): Serializable {
    companion object {
        private val serialVersionUID: Long = 1L
    }
}