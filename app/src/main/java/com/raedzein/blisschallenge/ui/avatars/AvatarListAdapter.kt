package com.raedzein.blisschallenge.ui.avatars

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.raedzein.blisschallenge.R
import com.raedzein.blisschallenge.databinding.ListItemAvatarBinding
import com.raedzein.blisschallenge.databinding.ListItemEmojiBinding
import com.raedzein.blisschallenge.domain.model.Emoji
import com.raedzein.blisschallenge.domain.model.GithubUser

class AvatarListAdapter(private val onAvatarClick: (GithubUser) -> Unit) :
    ListAdapter<GithubUser, AvatarListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemAvatarBinding.inflate(LayoutInflater.from(parent.context),
                parent, false),onAvatarClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(item = getItem(position))

    class ViewHolder(
        private val binding: ListItemAvatarBinding,
        onAvatarClick: (GithubUser) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var user: GithubUser

        init {
            binding.root.setOnClickListener {
                onAvatarClick(user)
            }
        }

        fun bind(item: GithubUser) {
            user = item
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageViewEmoji)
        }


    }
}
