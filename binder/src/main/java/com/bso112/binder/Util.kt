package com.bso112.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


internal fun <T : ViewDataBinding> ViewGroup.createViewDataBinding(layoutResId: Int): T =
    DataBindingUtil.inflate(
        LayoutInflater.from(context),
        layoutResId,
        this,
        false
    )