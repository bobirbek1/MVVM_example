package com.example.restapi.ui.fragments.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.restapi.R
import com.example.restapi.databinding.RvItemPostsBinding
import com.example.restapi.model.Post

class PostsAdapter(private val itemClickListener: ((Post) -> Unit)) :
    RecyclerView.Adapter<PostsAdapter.VH>() {

    private var listPosts = listOf<Post>()

    fun setPosts(listPosts: List<Post>) {
        this.listPosts = listPosts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding: RvItemPostsBinding =
            DataBindingUtil.inflate(inflater, R.layout.rv_item_posts, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(listPosts[position],itemClickListener)
    }

    override fun getItemCount(): Int = listPosts.size

    class VH(
        private val binding: RvItemPostsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(post: Post, itemClickListener: (Post) -> Unit) {
            binding.postsModel = post
            itemView.setOnClickListener {
                itemClickListener.invoke(post)
            }
        }

    }
}