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
import com.example.myapplication.NewPostActivity.Companion.textArg
import com.example.myapplication.OnInteractionListener
import com.example.myapplication.PostsAdapter
import com.example.myapplication.databinding.FragmentFeedBinding
import com.example.myapplication.Post
import com.example.myapplication.PostViewModel


class FeedFragment : Fragment() {
private val viewModel: PostViewModel by viewModels(
    ownerProducer = ::requireParentFragment
)
override fun onCreateView(
    inflater:LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
):View? {
    val binding: FragmentFeedBinding = FragmentFeedBinding.inflate(
        inflater,
        container,
        false
    )
    val adapter = PostsAdapter(object : OnInteractionListener {
        override fun onVideo(post: Post) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=5mGdCO7kNF0"))
            startActivity(intent)
        }

        override fun onEdit(post: Post) {
            viewModel.edit(post)
            val bundle = Bundle()
            bundle.textArg = post.content
            findNavController().navigate(R.id.action_feedFragment_to_newPostActivity, bundle)
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
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)

        }
        override fun onAuthorClicked(post: Post) {
            val bundle = Bundle()
            bundle.putInt("postId", post.id)
            findNavController().navigate(R.id.action_feedFragment_to_oneFragmentPost, bundle)

        }

    })
    binding.list.adapter=adapter
    viewModel.data.observe(viewLifecycleOwner) { posts ->
        adapter.submitList(posts)
    }
    binding.add.setOnClickListener {

            findNavController().navigate(R.id.action_feedFragment_to_newPostActivity)
        }
    return binding.root

}


    }







