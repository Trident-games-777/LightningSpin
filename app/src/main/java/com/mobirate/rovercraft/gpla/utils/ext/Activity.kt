package com.mobirate.rovercraft.gpla.utils.ext

import android.app.Activity
import android.content.Intent

const val WEB_EXTRA = "url"

inline fun <reified T> Activity.startNextWeb(url: String) {
    val intent = Intent(this, T::class.java)
    intent.putExtra(WEB_EXTRA, url)
    startActivity(intent)
    finish()
}

inline fun <reified T> Activity.startNext() {
    startActivity(Intent(this, T::class.java))
    finish()
}