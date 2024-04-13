package com.example.myapplication

data class Post (
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    var like: Int,
    var share: Int,
    val likedByMe: Boolean
)