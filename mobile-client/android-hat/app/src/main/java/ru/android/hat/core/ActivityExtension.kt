package ru.android.hat.core

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}