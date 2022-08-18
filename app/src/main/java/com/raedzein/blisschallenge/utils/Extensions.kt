package com.raedzein.blisschallenge.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.showMessageDialog(
    message: String,
    confirmButtonText: String,
    title: String? = null,
    cancellable: Boolean = false,
    confirmAction: (() -> Unit)? = null,
    negativeButtonText: String? = null,
    negativeAction: (() -> Unit)? = null
) {
    var builder = MaterialAlertDialogBuilder(this)
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
