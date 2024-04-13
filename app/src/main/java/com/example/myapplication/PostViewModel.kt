package com.example.myapplication

import androidx.lifecycle.ViewModel

import com.example.myapplication.PostRepository
import com.example.myapplication.PostRepositoryInMemoryImpl


class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likeById(id: Int)=repository.likeById(id)
    fun shareById(id: Int)=repository.shareById(id)
}