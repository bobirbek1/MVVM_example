package com.example.restapi.ui.fragments.post

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.util.LruCache
import com.example.restapi.BUNDLE_POST
import com.example.restapi.R
import com.example.restapi.databinding.PostFragmentBinding
import com.example.restapi.memory.MemoryCache
import com.example.restapi.model.Post
import com.example.restapi.ui.fragments.listeners.DataListener
import com.example.restapi.ui.fragments.post.adapter.CommentsAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class PostFragment : Fragment(), DataListener {


    private lateinit var viewModel: PostViewModel
    private lateinit var binding: PostFragmentBinding
    private var post: Post? = null
    private lateinit var adapter: CommentsAdapter

    private val TAG = "PostFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.post_fragment, container, false)
        binding.lifecycleOwner = this
        setAllViews()
        return binding.root
    }

    private fun setAllViews() {
        post = getArgs()
        requireActivity().pb_main.visibility = View.VISIBLE
        val factory = PostViewModelFactory(requireContext(), this)
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
        setRv()
        if (post != null) {
            binding.postViewModel = viewModel
            binding.postModel = post
            viewModel.getCommentsFromDB(post!!.id)
            getUser()
            getComments()
        }
    }

    private fun getUser() {
        viewModel.getUserById(post!!.userId)
        viewModel.getUser()?.observe(viewLifecycleOwner){user ->
            if (user != null){
            } else {
                viewModel.fetchUserByIdFromNetwork(post!!.userId)
            }
        }
    }

    private fun getComments() {
        viewModel.getComments()?.observe(viewLifecycleOwner, { comments ->
            Log.d(TAG, "getComments: $comments")
            if (!comments.isNullOrEmpty()) {
                adapter.setData(comments)
                requireActivity().pb_main.visibility = View.GONE
            } else {
                if (MemoryCache.getNetworkAvailable()) {
                    viewModel.fetchCommentsByPostIdFromNetwork(post!!.id)
                }
            }
        })
    }

    private fun setRv() {
        adapter = CommentsAdapter()
        binding.rvPostComment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPostComment.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvPostComment.adapter = adapter
    }

    private fun getArgs(): Post? {
        val jsonString = arguments?.getString(BUNDLE_POST, "")
        if (!jsonString.isNullOrEmpty()) {
            return Gson().fromJson(jsonString, Post::class.java)
        }
        return null
    }

    override fun onSuccess() {

    }

    override fun onStartFetching() {

    }

    override fun onFailure(error: String) {

    }


}