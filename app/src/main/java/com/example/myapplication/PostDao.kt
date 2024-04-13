package ru.btpit.zadanie2.PostDao
import com.example.myapplication.Post

interface PostDao {
    fun getAll(): List<Post>
    fun like(id:Int)
    fun share(id:Int)
    fun save(post: Post): Post
    fun removeById(id: Int)
}
