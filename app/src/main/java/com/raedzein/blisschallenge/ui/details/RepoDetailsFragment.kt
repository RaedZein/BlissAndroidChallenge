package com.raedzein.blisschallenge.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.raedzein.blisschallenge.databinding.FragmentRepoDetailsBinding
import com.raedzein.blisschallenge.ui.base.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RepoDetailsFragment : ViewBindingFragment<FragmentRepoDetailsBinding>() {

    private val args: RepoDetailsFragmentArgs by navArgs()

    override fun getBinding(layoutInflater: LayoutInflater, container: ViewGroup?) =
        FragmentRepoDetailsBinding.inflate(layoutInflater, container, false)


    override fun setUpViews() {

        binding.textViewRepoName.text = args.repo.name
        binding.textViewDescription.text = args.repo.description

        Glide.with(requireContext())
            .load(args.repo.owner.avatarUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageViewOwner)
    }

}
