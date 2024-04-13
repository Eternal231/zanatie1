package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Post


class PostRepositoryInMemoryImpl : PostRepository{
    private var post = Post(
        id = 1,
        author = "Государственное бюджетное профессиональное образовательное учреждение",
        content = "ГБПОУ ВО «БТПИТ» образовано в соответствии с  постановлением правительства Воронежской области от 20 мая 2015 года № 401 в результате реорганизации в  форме слияния государственного образовательного бюджетного учреждения среднего профессионального образования Воронежской области «Борисоглебский индустриальный техникум», \\nhttps://btpit36.ru/",
        published = "11 августа в 20:15",
        like = 999999,
        share = 999,
        likedByMe = false
        )
    private val data = MutableLiveData(post)
    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        if (post.likedByMe) post.like++ else post.like--
        data.value = post
    }
    override fun share() {
        post.share++
        data.value = post
    }
}


