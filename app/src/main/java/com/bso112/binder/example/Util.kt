package com.bso112.binder.example

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

fun ImageView.loadImage(imageUrl: String, @ColorRes placeHolderColorRes: Int) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(placeHolderColorRes)
        .into(this)
}


fun <T> Any?.cast(): T? = this as? T

fun <T> Any?.cast(success: (T) -> Unit): T? = cast<T>()?.also(success)

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

fun <T : ViewDataBinding> ViewGroup.createViewDataBinding(layoutResId: Int): T = DataBindingUtil.inflate(
    LayoutInflater.from(context),
    layoutResId,
    this,
    false
)
