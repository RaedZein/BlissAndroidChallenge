package com.raedzein.blisschallenge.ui.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.raedzein.blisschallenge.R
import com.raedzein.blisschallenge.databinding.FragmentRepoListBinding
import com.raedzein.blisschallenge.domain.model.GithubRepo
import com.raedzein.blisschallenge.ui.base.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RepoListFragment : ViewBindingFragment<FragmentRepoListBinding>() {

    override fun getBinding(layoutInflater: LayoutInflater, container: ViewGroup?) =
        FragmentRepoListBinding.inflate(layoutInflater, container, false)


    private val repoListAdapter by lazy {
        RepoListAdapter(::openDetailsPage)
    }

    private val listingViewModel by viewModels<ReposListingViewModel>()

    override fun setUpViews() {

        setObservers()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            repoListAdapter.refresh()
        }
        binding.recyclerView.adapter = repoListAdapter


    }
    private fun setObservers() {

        listingViewModel.reposListingLiveData.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                repoListAdapter.submitData(it)
            }
        }
        repoListAdapter.loadStateFlow.asLiveData().observe(viewLifecycleOwner){loadStates->
            showListLoading(shouldShow = loadStates.refresh is LoadState.Loading)

            when (loadStates.refresh) {
                is LoadState.Error -> {
                    val error = (loadStates.refresh as LoadState.Error).error
                    showMessageDialog(
                        message = error.message?:"",
                        confirmButtonText = getString(R.string.common_button_retry),
                        confirmAction = repoListAdapter::refresh)
                }
            }

        }
    }

    private fun showListLoading(shouldShow: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = shouldShow
    }

    private fun openDetailsPage(repo: GithubRepo) {
        findNavController().navigate(
            RepoListFragmentDirections
                .actionRepoListingFragmentToRepoDetailsFragment(repo))
    }

    private fun showMessageDialog(
        message: String,
        confirmButtonText: String,
        title: String? = null,
        cancellable: Boolean = false,
        confirmAction: (() -> Unit)? = null,
        negativeButtonText: String? = null,
        negativeAction: (() -> Unit)? = null
    ) {
        var builder = MaterialAlertDialogBuilder(requireContext())
        if(title != null)
            builder = builder.setTitle(title)
        if(negativeButtonText != null)
            builder = builder
                .setNegativeButton(negativeButtonText) { _, _ ->
                    negativeAction?.invoke()
                }
        builder.setMessage(message)
            .setCancelable(cancellable)
            .setPositiveButton(confirmButtonText) { _, _ ->
                confirmAction?.invoke()
            }
            .create().show()
    }

}
