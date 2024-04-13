package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity<TextView> : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=67DVTWhlYLk"))
                startActivity(intent)
            }
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }
            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, getString(R.string.postshare))
                startActivity(shareIntent)
            }
        })
        binding.list.adapter=adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }
        viewModel.edited.observe(this){ post->
            if(post.id == 0){
                return@observe
            }
            newPostLauncher.launch(post.content)
        }
        binding.cancel.setOnClickListener {
            newPostLauncher.launch(null)
//            with(binding.content){
//                viewModel.save()
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//                binding.group.visibility = View.GONE
//            }
        }
        binding.add.setOnClickListener {
            newPostLauncher.launch(null)
//            with(binding.content){
//                if(text.isNullOrBlank()){
//                    Toast.makeText(
//                        this@MainActivity,
//                        context.getString(R.string.error_empty_content),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//                viewModel.changeContent(text.toString())
//                viewModel.save()
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//            }
        }
        binding.add.setOnClickListener{
            newPostLauncher.launch(null)
        }

    }

}
