package com.example.restapi.ui.fragments.profile.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.restapi.R
import com.example.restapi.databinding.RvItemPostsBinding
import com.example.restapi.model.Post

class Adapter(private val itemClickListener: ((Post) -> Unit)) : RecyclerView.Adapter<Adapter.VH>() {

    private val TAG = "Adapter"

    private var listPosts = listOf<Post>()

    fun setData(listPosts: List<Post>) {
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

        holder.itemView.setOnClickListener {
            itemClickListener.invoke(listPosts[position])
        }
        holder.onBind(listPosts[position])
    }

    override fun getItemCount() = listPosts.size

    class VH(private val binding: RvItemPostsBinding) : RecyclerView.ViewHolder(binding.root) {
        private val TAG = "ViewHolder"

        fun onBind(post: Post) {
            binding.postsModel = post
        }
    }

}