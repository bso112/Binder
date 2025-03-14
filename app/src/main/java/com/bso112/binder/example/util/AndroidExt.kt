package com.bso112.binder.example.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ImageView.loadImage(imageUrl: String, @ColorRes placeHolderColorRes: Int) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(placeHolderColorRes)
        .into(this)
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}


fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

fun LifecycleOwner.repeatOnCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED, block)
    }
}

