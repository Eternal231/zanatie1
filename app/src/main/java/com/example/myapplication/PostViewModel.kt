package com.example.myapplication

import androidx.lifecycle.ViewModel

import com.example.myapplication.PostRepository
import com.example.myapplication.PostRepositoryInMemoryImpl


class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like()=repository.like()
    fun share()=repository.share()
}