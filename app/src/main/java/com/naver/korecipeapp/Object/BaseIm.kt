package com.naver.korecipeapp.Object

import java.io.Serializable

data class BaseIm(
    var base: base,
    var byteimage: ByteArray
):Serializable{
    companion object {
        private val serialVersionUID: Long = 1L
    }
}