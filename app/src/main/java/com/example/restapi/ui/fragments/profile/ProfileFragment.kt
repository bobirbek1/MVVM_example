package com.example.restapi.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restapi.BUNDLE_POST
import com.example.restapi.BUNDLE_USER
import com.example.restapi.R
import com.example.restapi.databinding.ProfileFragmentBinding
import com.example.restapi.model.User
import com.example.restapi.toast
import com.example.restapi.ui.fragments.listeners.DataListener
import com.example.restapi.ui.fragments.profile.adapter.Adapter
import com.google.gson.Gson

class ProfileFragment : Fragment(),DataListener {

    private val TAG = "ProfileFragment"

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var adapter: Adapter
    private lateinit var gson: Gson

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        setRv()
        return binding.root
    }

    private fun setRv() {
        adapter = Adapter {post ->
            val bundle = Bundle()
            val jsonString = gson.toJson(post)
            bundle.putString(BUNDLE_POST,jsonString)
            findNavController().navigate(R.id.postFragment,bundle)
        }
        binding.rvAlbum.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAlbum.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ProfileViewModelFactory(requireContext(),this)
        viewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        setAllViews()
    }

    private fun setAllViews() {
        gson = Gson()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        getUserFromArgs()
        viewModel.getPosts()?.observe(viewLifecycleOwner){listPosts ->
            if (listPosts != null)
            adapter.setData(listPosts)
            else
                requireContext().toast("listPosts is null. Something went wrong")
        }
    }

    private fun getUserFromArgs() {
        val jsonString = requireArguments().getString(BUNDLE_USER, "")
        if (!jsonString.isNullOrEmpty()) {
            val user = Gson().fromJson(jsonString, User::class.java)
            binding.user = user
            viewModel.fetchPostsByUserId(user.id)
        } else {
            requireContext().toast("User info not found")
        }
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