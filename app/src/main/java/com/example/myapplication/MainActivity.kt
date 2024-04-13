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
        viewModel.data.observe(this) { post ->
            with(binding) {
                textView2.text = post.author
                textView3.text = post.published
                textView5.text = post.content
                textView7.text = post.like.toString()
                textView9.text = post.share.toString()
                imageView11.setImageResource(
                    if (post.likedByMe) R.drawable.like_krasn else R.drawable.heart
                )
                textView7.text = post.like.toString()
                when {
                    post.like in 1000..999999 -> textView7.text = "${post.like / 1000}K"
                    post.like < 1000 -> textView7.text = post.like.toString()
                    else -> textView7.text = String.format("%.1fM", post.like.toDouble() / 1000000)
                }
                textView9.text = post.share.toString()
                when {
                    post.share < 1000 -> textView9.text = post.share.toString()
                    post.share in 1000..999999 -> textView9.text = "${post.share / 1000}K"
                    else -> textView9.text = String.format(
                        "%.1fM", post.share.toDouble() / 1000000
                    )
                }
            }
            binding.imageView11.setOnClickListener {
                viewModel.like()
            }
            binding.imageView12.setOnClickListener {
                viewModel.share()
            }
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
