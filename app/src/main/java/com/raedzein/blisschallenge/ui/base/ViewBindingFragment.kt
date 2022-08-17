package com.raedzein.blisschallenge.ui.base

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class ViewBindingFragment<B: ViewBinding> : Fragment(){

    private var _binding: B? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    abstract fun getBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    abstract fun setUpViews()


}
