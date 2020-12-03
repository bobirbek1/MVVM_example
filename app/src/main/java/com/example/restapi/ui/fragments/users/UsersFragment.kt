package com.example.restapi.ui.fragments.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restapi.BUNDLE_USER
import com.example.restapi.R
import com.example.restapi.toast
import com.example.restapi.ui.MainViewModel
import com.example.restapi.ui.fragments.listeners.DataListener
import com.example.restapi.ui.fragments.users.adapter.UsersAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_users.view.*

class UsersFragment : Fragment(), DataListener {

    private lateinit var usersViewModel: UsersViewModel
    private lateinit var rootView: View
    private lateinit var adapter: UsersAdapter

    private val TAG = "UsersFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_users, container, false)
        setAllViews()
        return rootView
    }

    private fun setAllViews() {
        val factory = UsersViewModelFactory(requireContext(),this)
        usersViewModel =
            ViewModelProvider(this,factory).get(UsersViewModel::class.java)
        requireActivity().pb_main.visibility = View.VISIBLE
        setRv()
        usersViewModel.fetchUsers()
        requireActivity().pb_main.visibility = View.VISIBLE
        usersViewModel.getUsers()?.observe(viewLifecycleOwner, { listUsers ->
            requireActivity().pb_main.visibility = View.GONE
            adapter.setData(listUsers)
        })
    }

    private fun setRv() {
        adapter = UsersAdapter{user ->
            Log.d(TAG, "setRv: ${user.username}")
            val bundle = Bundle()
            val jsonString = Gson().toJson(user)
            bundle.putString(BUNDLE_USER,jsonString)
            findNavController().navigate(R.id.profileFragment,bundle)
        }
        rootView.rv_users.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().pb_main.visibility = View.GONE
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