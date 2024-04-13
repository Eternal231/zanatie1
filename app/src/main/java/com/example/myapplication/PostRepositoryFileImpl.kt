package com.example.myapplication

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepositoryFileImpl(private val context: Context, ) : PostRepository {
    private val gson = Gson()
    private val filename = "posts.json"
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private var nextId = 1
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()){
            context.openFileInput(filename).bufferedReader().use{
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            sync()
        }

    }

    override fun getAll(): LiveData<List<Post>> = data
    override fun save(post: Post) {
        if (post.id == 0) {
            // TODO: remove hardcoded author & published
            this.posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "DEMENSKY",
                    likedByMe = false,
                    published = "now"
                )
            ) + this.posts


            data.value = this.posts
            sync()
            return
        }

        this.posts = this.posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = this.posts
        sync()
    }
    override fun likeById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else
                it.copy(likedByMe = !it.likedByMe, like = if (!it.likedByMe) it.like+1 else it.like-1)
        }
        data.value = posts
        sync()
    }
    override fun shareById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else
                it.copy(share = it.share+1)
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Int) {
        posts = posts.filter { it.id!=id }
        data.value = posts
        sync()
    }

    override fun postID(id: Int): LiveData<Post> {
        val postLiveData = MutableLiveData<Post>()
        postLiveData.value = posts.find { it.id == id }

        return postLiveData
    }
    private fun sync(){

        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }


}

