package com.raedzein.blisschallenge.ui.custom_view

import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.raedzein.blisschallenge.R
import com.raedzein.blisschallenge.databinding.ViewCustomSearchBarBinding
import com.raedzein.blisschallenge.utils.KeyboardUtils

class CustomSearchBarHandler {

    lateinit var searchEditText: TextInputEditText
    private lateinit var clearImageView: ImageView
    private lateinit var buttonSearch: Button
    private var searchBarListener : SearchBarListener? = null

    fun setUpViews(
        binding: ViewCustomSearchBarBinding,
        searchBarListener: SearchBarListener) {

        this.searchEditText = binding.editTextSearch
        this.clearImageView = binding.imageViewClear
        this.buttonSearch = binding.buttonSearch
        this.searchBarListener = searchBarListener

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                validateSearch()
                true
            } else false
        }
        buttonSearch.setOnClickListener {
            validateSearch()
        }

        clearImageView.setOnClickListener {
            searchEditText.setText("")
            KeyboardUtils.hideKeyboard(currentView = searchEditText)
            searchEditText.clearFocus()
        }

        searchEditText.setOnFocusChangeListener { view, focus ->
            if(focus) {
                KeyboardUtils.showKeyboard(view)
                searchBarListener?.onSearchFocus()
            } else {
                KeyboardUtils.hideKeyboard(currentView = view)
                if(searchEditText.text.isNullOrEmpty())
                    searchBarListener?.onSearchUnFocus()
            }
        }
        searchEditText.addTextChangedListener {
            clearImageView.isVisible = searchEditText.text.toString().isNotEmpty()
            searchBarListener?.onSearchTextChanged(searchEditText.text.toString())
        }
    }

    private fun validateSearch() {
        if(searchEditText.text.isNullOrEmpty()){
            searchEditText.error = searchEditText.context.getString(R.string.avatar_err_empty)
            return
        }
        searchEditText.error = null
        KeyboardUtils.hideKeyboard(currentView = searchEditText)
        searchEditText.clearFocus()

        searchBarListener?.onSearchClicked()
    }


    fun setQueryText(queryText: String) {
        searchEditText.setText(queryText)
    }

    fun enable(isEnabled: Boolean) {
        searchEditText.isEnabled = isEnabled
        buttonSearch.isEnabled = isEnabled
    }

    interface SearchBarListener{
        fun onSearchClicked()
        fun onSearchTextChanged(text: String)
        fun onSearchFocus()
        fun onSearchUnFocus()
    }
}
