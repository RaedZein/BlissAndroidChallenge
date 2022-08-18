package com.raedzein.blisschallenge.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author Raed Zein
 */

object KeyboardUtils {
    fun hideKeyboard(activity: Activity? = null, currentView: View? = null) {
        val context = currentView?.context?:activity?:return
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        //Find the currently focused view, so we can grab the correct window token from it.
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        val view = currentView ?: activity?.currentFocus ?: View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(currentView: View) {
        val imm = currentView.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(currentView, InputMethodManager.SHOW_IMPLICIT)
    }
}