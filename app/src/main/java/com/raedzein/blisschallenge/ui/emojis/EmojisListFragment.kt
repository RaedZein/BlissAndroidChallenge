package com.raedzein.blisschallenge.ui.emojis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.raedzein.blisschallenge.R
import com.raedzein.blisschallenge.databinding.FragmentEmojisListBinding
import com.raedzein.blisschallenge.ui.base.ViewBindingFragment
import com.raedzein.blisschallenge.ui.base.ViewLoaderState
import com.raedzein.blisschallenge.utils.showMessageDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EmojisListFragment : ViewBindingFragment<FragmentEmojisListBinding>() {

    private val emojisViewModel by viewModels<EmojisListViewModel>()
    private val adapter :EmojiListAdapter = EmojiListAdapter{
        emojisViewModel.removeEmojiFromDb(it)
    }


    override fun getBinding(layoutInflater: LayoutInflater, container: ViewGroup?) =
        FragmentEmojisListBinding.inflate(layoutInflater, container, false)

    override fun setUpViews() {
        setUpLiveDataObservers()
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),4)
        binding.recyclerView.adapter = adapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            emojisViewModel.refreshEmojisFromRemoteApi()
        }
    }

    private fun setUpLiveDataObservers() {
        emojisViewModel.emojiLiveData.observe(viewLifecycleOwner,adapter::submitList)

        emojisViewModel.fetchEmojiLiveData.observe(viewLifecycleOwner){
            when(it){
                ViewLoaderState.Loading ->
                    binding.swipeRefreshLayout.isRefreshing = true
                ViewLoaderState.Init,
                is ViewLoaderState.Loaded ->
                    binding.swipeRefreshLayout.isRefreshing = false

                is ViewLoaderState.Failed -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    requireContext().showMessageDialog(
                        message = it.error.message?:"",
                        confirmButtonText = getString(R.string.common_button_retry),
                        confirmAction = emojisViewModel::refreshEmojisFromRemoteApi)
                }
            }
        }
    }


}
