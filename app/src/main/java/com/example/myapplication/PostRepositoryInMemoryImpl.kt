package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Post


class PostRepositoryInMemoryImpl : PostRepository{
    private var posts = listOf(
        Post(
            id = 1,
            author = "Государственное бюджетное профессиональное образовательное учреждение",
            content = "ГБПОУ ВО «БТПИТ» образовано в соответствии с  постановлением правительства Воронежской области от 20 мая 2015 года № 401 в результате реорганизации в  форме слияния государственного образовательного бюджетного учреждения среднего профессионального образования Воронежской области «Борисоглебский индустриальный техникум», \\nhttps://btpit36.ru/",
            published = "11 августа в 20:15",
            like = 999,
            share = 99,
            likedByMe = false,
            shareByMe=false
        ),
        Post(
            id = 2,
            author = "Государственное бюджетное профессиональное образовательное учреждение",
            content = "ГБПОУ ВО «БТПИТ» образовано в соответствии с  постановлением правительства Воронежской области от 20 мая 2015 года № 401 в результате реорганизации в  форме слияния государственного образовательного бюджетного учреждения среднего профессионального образования Воронежской области «Борисоглебский индустриальный техникум», \\nhttps://btpit36.ru/",
            published = "12 августа в 20:15",
            like = 9999,
            share = 999,
            likedByMe = false,
            shareByMe=false
        ),
        Post(
            id = 3,
            author = "Государственное бюджетное профессиональное образовательное учреждение",
            content = "Новости бтпит - переходи по ссылке, \\nhttps://btpit36.ru/",
            published = "13 августа в 20:15",
            like = 999999,
            share = 9999,
            likedByMe = false,
            shareByMe=false
        ),
    )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Int) {
        posts = posts.map {
            if(it.id!= id.toInt()) it else it.copy(likedByMe = !it.likedByMe)
        }
        posts.map{
            if(it.likedByMe && it.id == id.toInt()) it.like++ else it
        }
        posts.map {
            if(!it.likedByMe && it.id == id.toInt()) it.like-- else it
        }
        data.value = posts
    }
    override fun shareById(id: Int) {
        posts = posts.map {
            if(it.id!= id.toInt()) it else it.copy(shareByMe = !it.shareByMe)
        }
        posts.map {
            if (it.id != id.toInt()) it else it.share++
        }
        data.value = posts
    }
}


