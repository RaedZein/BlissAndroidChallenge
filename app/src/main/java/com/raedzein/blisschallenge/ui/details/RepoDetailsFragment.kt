package com.raedzein.blisschallenge.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.raedzein.assignment.R
import com.raedzein.assignment.databinding.FragmentRepoDetailsBinding
import com.raedzein.assignment.domain.model.GithubRepo
import com.raedzein.assignment.ui.base.ViewBindingFragment
import com.raedzein.assignment.ui.list.FavouriteLoaderView
import com.raedzein.blisschallenge.databinding.FragmentRepoDetailsBinding
import com.raedzein.blisschallenge.ui.base.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RepoDetailsFragment : ViewBindingFragment<FragmentRepoDetailsBinding>() {

    private val args: RepoDetailsFragmentArgs by navArgs()

    override fun getBinding(layoutInflater: LayoutInflater, container: ViewGroup?) =
        FragmentRepoDetailsBinding.inflate(layoutInflater, container, false)


    override fun setUpViews() {
        repoDetailsViewModel.githubReposLiveData.observe(viewLifecycleOwner,::showRepoDetails)

        val favouriteLoaderView = FavouriteLoaderView(binding.lottieViewHeart)
        repoDetailsViewModel.favouritedLiveData.observe(viewLifecycleOwner,favouriteLoaderView::favourite)

        binding.lottieViewHeart.setOnClickListener {
            repoDetailsViewModel.setFavourite(args.repoId,
                !favouriteLoaderView.favourited)
        }
    }

    private fun showRepoDetails(repo: GithubRepo) {

        binding.textViewRepoName.text = repo.name
        binding.textViewDescription.text = repo.description

        Glide.with(requireContext())
            .load(repo.owner.avatarUrl)
            .placeholder(R.color.colorPrimaryDark)
            .circleCrop()
            .into(binding.imageViewOwner)

    }

}
