package com.raedzein.blisschallenge.ui.repos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.raedzein.blisschallenge.R
import com.raedzein.blisschallenge.databinding.ItemListRepositoryBinding
import com.raedzein.blisschallenge.domain.model.GithubRepo

class RepoListAdapter(private val openRepo: (GithubRepo) -> Unit) :
    PagingDataAdapter<GithubRepo, RepoListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<GithubRepo>() {
            override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListRepositoryBinding.inflate(LayoutInflater.from(parent.context),
                parent, false),openRepo)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(item = getItem(position))

    class ViewHolder(
        private val binding: ItemListRepositoryBinding,
        openRepo: (GithubRepo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var repo: GithubRepo

        init {
            binding.root.setOnClickListener {
                openRepo(repo)
            }
        }

        fun bind(item: GithubRepo?) {
            if(item != null) {
                repo = item

                binding.textViewName.text = item.name
                binding.textViewDescription.text = item.description

                binding.textViewStarsCount.text = item.starsCount.toString()

                Glide.with(itemView.context)
                    .load(item.owner.avatarUrl)
                    .placeholder(R.color.teal_200)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imageViewOwner)
                binding.textViewOwnerName.text = item.owner.username
            }
        }


    }
}
