package com.raedzein.blisschallenge.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.raedzein.blisschallenge.ui.custom_view.CustomSearchBar
import com.raedzein.blisschallenge.utils.KeyboardUtils
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED


@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(),CustomSearchBar.SearchBarListener {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun getBinding(layoutInflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    private val sheetView by lazy {
        binding.bottomSheetSearchUser
    }

    private val bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout> by lazy{
        BottomSheetBehavior.from(sheetView.constraintLayoutSearch)
    }
    override fun setUpViews() {
        setUpLiveDataObservers()

        binding.buttonRandomEmoji.setOnClickListener {
            homeViewModel.getRandomEmoji()
        }
        binding.cardViewEmojis.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToEmojisListFragment()
            )
        }
        binding.cardViewAvatars.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAvatarListFragment()
            )
        }
        binding.cardViewRepos.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToRepoListFragment()
            )
        }
        sheetView.searchBar.setSearchListener(this)
        sheetView.searchBar.setQueryText(homeViewModel.getUserNameText())

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(bottomSheetBehavior.state == STATE_EXPANDED)
                        onSearchUnFocus()
                    else
                        activity?.finish()
                }
            })

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
                        confirmAction = homeViewModel::getRandomEmoji,
                        cancellable = true)
                }
            }
        }
        homeViewModel.githubUserLiveData.observe(viewLifecycleOwner){
            when(it){
                ViewLoaderState.Init -> {
                    sheetView.progressBarSearchUser.isVisible = false
                    sheetView.searchBar.enable(true)
                    sheetView.textViewMessage.isVisible = true
                    sheetView.textViewNoUserFoundMessage.isVisible = false
                    sheetView.imageViewGithubUser.isInvisible = true
                    sheetView.textViewGithubUsername.isInvisible = true
                }
                ViewLoaderState.Loading -> {
                    sheetView.progressBarSearchUser.isVisible = true
                    sheetView.searchBar.enable(false)
                    sheetView.textViewMessage.isVisible = false
                    sheetView.textViewNoUserFoundMessage.isVisible = false
                    sheetView.imageViewGithubUser.isInvisible = true
                    sheetView.textViewGithubUsername.isInvisible = true
                }
                is ViewLoaderState.Loaded -> {
                    sheetView.progressBarSearchUser.isVisible = false
                    sheetView.searchBar.enable(true)
                    sheetView.textViewMessage.isVisible = false
                    sheetView.textViewNoUserFoundMessage.isVisible = false
                    sheetView.imageViewGithubUser.isVisible = true
                    Glide.with(requireContext())
                        .load(it.result.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(sheetView.imageViewGithubUser)
                    sheetView.textViewGithubUsername.isVisible = true

                    sheetView.textViewGithubUsername.text = it.result.username
                }
                is ViewLoaderState.Failed -> {
                    sheetView.progressBarSearchUser.isVisible = false
                    sheetView.searchBar.enable(true)
                    sheetView.textViewMessage.isVisible = false
                    sheetView.textViewNoUserFoundMessage.isVisible = true
                    sheetView.imageViewGithubUser.isInvisible = true
                    sheetView.textViewGithubUsername.isInvisible = true
                }
            }
        }
    }

    override fun onSearchClicked()  = homeViewModel.searchForGithubUser()

    override fun onSearchTextChanged(text: String) = homeViewModel.setUserNameText(text)

    override fun onSearchFocus() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onSearchUnFocus() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        sheetView.searchBar.searchEditText.clearFocus()

        sheetView.progressBarSearchUser.isVisible = false
        sheetView.searchBar.enable(true)
        sheetView.textViewMessage.isVisible = true
        sheetView.textViewNoUserFoundMessage.isVisible = false
        sheetView.imageViewGithubUser.isInvisible = true
        sheetView.textViewGithubUsername.isInvisible = true
    }


}
