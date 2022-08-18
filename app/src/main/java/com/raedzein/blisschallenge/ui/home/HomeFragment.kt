package com.raedzein.blisschallenge.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.raedzein.blisschallenge.R
import com.raedzein.blisschallenge.databinding.FragmentHomeBinding
import com.raedzein.blisschallenge.ui.base.ViewBindingFragment
import com.raedzein.blisschallenge.ui.base.ViewLoaderState
import com.raedzein.blisschallenge.utils.showMessageDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun getBinding(layoutInflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun setUpViews() {
        setUpLiveDataObservers()

        binding.buttonRandomEmoji.setOnClickListener {
            homeViewModel.getRandomEmoji()
        }
        binding.cardViewCategoryEmojis.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToEmojisListFragment())
        }
    }

    private fun setUpLiveDataObservers() {
        homeViewModel.randomEmojiLiveData.observe(viewLifecycleOwner){
            when(it){
                ViewLoaderState.Init -> {
                    binding.progressBar.isVisible = false
                    binding.imageViewEmoji.isVisible = false
                    binding.buttonRandomEmoji.setText(R.string.home_button_fetch_emoji_list)
                }
                ViewLoaderState.Loading -> {
                    binding.imageViewEmoji.isInvisible = true
                    binding.buttonRandomEmoji.isEnabled = false
                    binding.progressBar.isVisible = true
                }
                is ViewLoaderState.Loaded -> {
                    binding.imageViewEmoji.isVisible = true
                    binding.buttonRandomEmoji.isEnabled = true
                    binding.progressBar.isVisible = false

                    Glide.with(requireContext())
                        .load(it.result.url)
                        .placeholder(R.color.teal_200)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.imageViewEmoji)
                    binding.buttonRandomEmoji.setText(R.string.home_button_random_emoji)
                }
                is ViewLoaderState.Failed -> {
                    binding.buttonRandomEmoji.isEnabled = true
                    binding.imageViewEmoji.isInvisible = true
                    binding.progressBar.isVisible = false
                    requireContext().showMessageDialog(
                        message = it.error.message?:"",
                        confirmButtonText = getString(R.string.common_button_retry),
                        confirmAction = homeViewModel::getRandomEmoji)
                }
            }
        }
    }


}
