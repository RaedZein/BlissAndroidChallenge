package com.raedzein.blisschallenge.ui.avatars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.raedzein.blisschallenge.databinding.FragmentAvatarsBinding
import com.raedzein.blisschallenge.databinding.FragmentEmojisListBinding
import com.raedzein.blisschallenge.ui.base.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AvatarListFragment : ViewBindingFragment<FragmentAvatarsBinding>() {

    private val emojisViewModel by viewModels<AvatarListViewModel>()
    private val adapter :AvatarListAdapter = AvatarListAdapter{
        emojisViewModel.removeGithubUserFromDb(it)
    }


    override fun getBinding(layoutInflater: LayoutInflater, container: ViewGroup?) =
        FragmentAvatarsBinding.inflate(layoutInflater, container, false)

    override fun setUpViews() {
        setUpLiveDataObservers()
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        binding.recyclerView.adapter = adapter
    }

    private fun setUpLiveDataObservers() {
        emojisViewModel.emojiLiveData.observe(viewLifecycleOwner,adapter::submitList)
    }


}
