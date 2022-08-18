package com.raedzein.blisschallenge.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputEditText
import com.raedzein.blisschallenge.R
import com.raedzein.blisschallenge.utils.KeyboardUtils

class CustomSearchBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val searchEditText: TextInputEditText
    private val clearImageView: ImageView
    private val buttonSearch: Button
    private var searchBarListener : SearchBarListener? = null
    init {
        inflate(context, R.layout.view_custom_search_bar, this)
        searchEditText = findViewById(R.id.editTextSearch)
        clearImageView = findViewById(R.id.imageViewClear)
        buttonSearch = findViewById(R.id.buttonSearch)

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
            searchEditText.error = context.getString(R.string.avatar_err_empty)
            return
        }
        searchEditText.error = null
        KeyboardUtils.hideKeyboard(currentView = searchEditText)
        searchEditText.clearFocus()

        searchBarListener?.onSearchClicked()
    }

    fun setSearchListener(searchBarListener: SearchBarListener) {
        this.searchBarListener = searchBarListener
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
