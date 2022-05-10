package com.markolucic.cubes.events24.ui.view

import android.content.Context
import android.widget.Toast

object CustomToast {
    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(context: Context, resourceId: Int) {
        Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show()
    }
}