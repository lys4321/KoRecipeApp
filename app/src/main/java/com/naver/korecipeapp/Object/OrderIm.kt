package com.naver.korecipeapp.Object

import java.io.Serializable

class OrderIm(
    var order: cooking,
    var byteimage: ByteArray?
): Serializable{
    companion object {
        private val serialVersionUID: Long = 1L
    }
}