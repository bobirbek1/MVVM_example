package com.example.restapi.ui.fragments.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restapi.BUNDLE_POST
import com.example.restapi.R
import com.example.restapi.ui.fragments.listeners.DataListener
import com.example.restapi.ui.fragments.posts.adapter.PostsAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_posts.view.*

class PostsFragment : Fragment(), DataListener {

    private lateinit var postsViewModel: PostsViewModel
    private lateinit var rootView: View
    private lateinit var adapter: PostsAdapter
    private val TAG = "PostsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = PostsViewModelFactory(requireContext(), this)
        postsViewModel =
            ViewModelProvider(this, factory).get(PostsViewModel::class.java)
        rootView = inflater.inflate(R.layout.fragment_posts, container, false)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        setRv()
        postsViewModel.fetchAllPosts()
        requireActivity().pb_main.visibility = View.VISIBLE
        postsViewModel.getPosts()?.observe(this) { posts ->
            requireActivity().pb_main.visibility = View.GONE
            if (posts != null)
                adapter.setPosts(posts)
        }
    }

    private fun setRv() {
        adapter = PostsAdapter { post ->
            val bundle = Bundle()
            bundle.putString(BUNDLE_POST, Gson().toJson(post))
            findNavController().navigate(R.id.postFragment, bundle)
        }
        rootView.rv_posts.adapter = adapter
    }

    override fun onSuccess() {

    }

    override fun onStartFetching() {
        TODO("Not yet implemented")
    }

    override fun onFailure(error: String) {
        TODO("Not yet implemented")
    }
}