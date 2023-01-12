package com.naver.korecipeapp.Object

import java.io.Serializable

class ingredients(
    var recipe_code: String,
    var ing_name: String,
    var amount: String
): Serializable {
    companion object {
        private val serialVersionUID: Long = 1L
    }
}