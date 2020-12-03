package com.example.restapi.ui.fragments.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.restapi.R
import com.example.restapi.databinding.RvCommentItemBinding
import com.example.restapi.model.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.VH>() {

    private var listComments = listOf<Comment>()

    fun setData(listComment: List<Comment>) {
        this.listComments = listComment
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding: RvCommentItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.rv_comment_item, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(listComments[position])
    }

    override fun getItemCount(): Int = listComments.size

    class VH(private val binding: RvCommentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(comment: Comment) {
            binding.comment = comment
        }

    }

}