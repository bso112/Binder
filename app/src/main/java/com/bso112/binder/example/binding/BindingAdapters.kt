package com.bso112.binder.example.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bso112.binder.example.R
import com.bso112.binder.example.loadImage

@BindingAdapter("srcUrl")
fun ImageView.setSrcUrl(uri: String?) {
    uri?.let {
        loadImage(it, R.color.placeHolder)
    }
}


@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun RecyclerView.bindSubmitList(
    list: List<BindableUIModel>?
) {
    if (adapter == null) {
        adapter = BindingListAdapter()
    }
    (adapter as? ListAdapter<Any, *>)?.submitList(list)
}
