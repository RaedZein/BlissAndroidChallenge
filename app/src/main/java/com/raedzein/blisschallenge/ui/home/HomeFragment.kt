package com.raedzein.blisschallenge.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.raedzein.blisschallenge.databinding.FragmentHomeBinding
import com.raedzein.blisschallenge.ui.base.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {
    override fun getBinding(layoutInflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun setUpViews() {
    }


}
