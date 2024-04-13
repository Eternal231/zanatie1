package com.example.myapplication

import androidx.lifecycle.LiveData
import com.example.myapplication.Post


interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}