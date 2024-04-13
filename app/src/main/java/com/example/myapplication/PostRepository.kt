package com.example.myapplication

import androidx.lifecycle.LiveData
import com.example.myapplication.Post


interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Int)
    fun postID(id: Int): LiveData<Post>
    fun shareById(id: Int)
    fun removeById(id: Int)
    fun save(post: Post)
}