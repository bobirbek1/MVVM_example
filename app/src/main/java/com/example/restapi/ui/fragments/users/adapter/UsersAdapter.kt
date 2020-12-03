package com.example.restapi.ui.fragments.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.restapi.R
import com.example.restapi.databinding.RvItemUsersBinding
import com.example.restapi.model.User

class UsersAdapter(private val itemClickListener: ((User) -> Unit)) :
    RecyclerView.Adapter<UsersAdapter.VH>() {

    private var listUsers = listOf<User>()

    fun setData(users: List<User>) {
        this.listUsers = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding: RvItemUsersBinding =
            DataBindingUtil.inflate(inflater, R.layout.rv_item_users, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(listUsers[position], itemClickListener)
    }

    override fun getItemCount(): Int = listUsers.size

    class VH(private val binding: RvItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User, itemClickListener: (User) -> Unit) {
            binding.userModel = user
            itemView.setOnClickListener {
                itemClickListener.invoke(user)
            }
        }
    }

}