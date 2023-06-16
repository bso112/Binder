package com.bso112.binder.example.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bso112.binder.example.R
import com.bso112.binder.example.util.loadImage

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

@BindingAdapter("refreshLayout")
fun RecyclerView.setRefreshLayout(refreshLayout: SwipeRefreshLayout) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val isRefreshEnabled = when (val lm = layoutManager) {
                is LinearLayoutManager -> lm.findFirstCompletelyVisibleItemPosition() == 0
                else -> true
            }
            if (isRefreshEnabled) {
                refreshLayout.isEnabled = true
            }
        }
    })
}

@BindingAdapter("visible")
fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
