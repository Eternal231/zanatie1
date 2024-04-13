package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.example.myapplication.PostViewModel
import com.example.myapplication.databinding.ActivityMainBinding

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val viewModel: PostViewModel by viewModels()
            val adapter = PostsAdapter({
                viewModel.likeById(it.id)
            },
                {
                    viewModel.shareById(it.id)
                })
            binding.list.adapter = adapter
            viewModel.data.observe(this) { posts ->
                adapter.submitList(posts)
            }
        }
    }

    @MainThread
    public inline fun <reified VM : ViewModel> ComponentActivity.viewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): Lazy<VM> {
        val factoryPromise = factoryProducer ?: {
            defaultViewModelProviderFactory
        }

        return ViewModelLazy(
            VM::class,
            { viewModelStore },
            factoryPromise,
            { this.defaultViewModelCreationExtras }
        )
    }
