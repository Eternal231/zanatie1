package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityOneFragmentPostBinding
import com.example.myapplication.StringArg
import com.example.myapplication.PostViewModel

class OneFragmentPost : Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ActivityOneFragmentPostBinding.inflate(
            inflater,
            container,
            false
        )
        val postId = arguments?.getInt("postId")
        postId?.let { id ->
            viewModel.getPostById(id)
            viewModel.selectedPost.observe(viewLifecycleOwner) { post ->
                binding.textView2.text = post.author
                binding.textView3.text = post.published
                binding.textView5.text = post.content
                binding.textView7.text = post.like.toString()
                binding.textView9.text = post.share.toString()
                binding.like.isChecked = post.likedByMe
                binding.like.text = formatNumber(post.like)
                binding.share.text = formatNumber1(post.share)
                binding.video.setOnClickListener {
                    val url = "https://www.youtube.com/watch?v=cnDLSMLAOY0"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
                binding.back.setOnClickListener {
                    findNavController().navigateUp()
                }
                binding.editorPost.setOnClickListener {
                    viewModel.edit(post)
//                val text = post.content
//                newPostActivity.launch(text)
                    val bundle = Bundle()
                    bundle.textArg = post.content
                    findNavController().navigate(R.id.action_oneFragmentPost_to_newPostActivity, bundle)
                }
                binding.like.setOnClickListener {
                    viewModel.likeById(postId)

                }
                binding.share.setOnClickListener {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(shareIntent)

                    viewModel.shareById(postId)
                }

                viewModel.data.observe(viewLifecycleOwner) { posts ->
                    val updatedPost = posts.find { it.id == postId }
                    updatedPost?.let {
                        binding.like.text = it.like.toString()
                        binding.share.text = it.share.toString()
                    }
                }



            }

            binding.del.setOnClickListener{
                viewModel.removeById(postId)
                findNavController().navigateUp()
            }



        }
        return binding.root
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_one_post_fragment)
//    }

}
private fun formatNumber(number: Int): String {
    return when {
        number >= 1000000 -> {
            val value = number / 1000000
            val remainder = number % 1000000
            if (remainder > 0) {
                if (remainder >= 100000) {
                    String.format("%.1f M", value + remainder / 1000000.0)
                } else {
                    String.format("%d.%d M", value, remainder / 100000)
                }
            } else {
                "$value M"
            }
        }
        number in 1000..9999 -> {
            String.format("%.1fK", number / 1000.0)
        }
        number >= 10000 -> {
            String.format("%dK", number / 1000)
        }
        else -> number.toString()
    }
}
private fun formatNumber1(number: Int): String {
    return when {
        number >= 1000000 -> {
            val value = number / 1000000
            val remainder = number % 1000000
            if (remainder > 0) {
                if (remainder >= 100000) {
                    String.format("%.1f M", value + remainder / 1000000.0)
                } else {
                    String.format("%d.%d M", value, remainder / 100000)
                }
            } else {
                "$value M"
            }
        }
        number in 1000..9999 -> {
            String.format("%.1fK", number / 1000.0)
        }
        number >= 10000 -> {
            String.format("%dK", number / 1000)
        }
        else -> number.toString()
    }
}