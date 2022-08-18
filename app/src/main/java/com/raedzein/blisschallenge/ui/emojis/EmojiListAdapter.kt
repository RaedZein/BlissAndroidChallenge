package com.raedzein.blisschallenge.ui.emojis

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.raedzein.blisschallenge.R
import com.raedzein.blisschallenge.databinding.ListItemEmojiBinding
import com.raedzein.blisschallenge.domain.model.Emoji

class EmojiListAdapter(private val onEmojiClick: (Emoji) -> Unit) :
    ListAdapter<Emoji, EmojiListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Emoji>() {
            override fun areItemsTheSame(oldItem: Emoji, newItem: Emoji): Boolean =
                oldItem.name == newItem.name

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Emoji, newItem: Emoji): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemEmojiBinding.inflate(LayoutInflater.from(parent.context),
                parent, false),onEmojiClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(item = getItem(position))

    class ViewHolder(
        private val binding: ListItemEmojiBinding,
        onEmojiClick: (Emoji) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var place: Emoji

        init {
            binding.root.setOnClickListener {
                onEmojiClick(place)
            }
        }

        fun bind(item: Emoji) {
            place = item
            Glide.with(itemView.context)
                .load(item.url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageViewEmoji)
        }


    }
}
