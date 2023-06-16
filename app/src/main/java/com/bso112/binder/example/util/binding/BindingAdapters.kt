package com.bso112.binder.example.util.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bso112.binder.example.R
import com.bso112.binder.example.util.paging.PagingStateHolder
import com.bso112.binder.example.util.loadImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

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

@BindingAdapter("submitData")
fun <T : BindableUIModel> RecyclerView.setPagingList(
    list: PagingData<T>?
) {
    post {
        val lifecycle = findViewTreeLifecycleOwner()?.lifecycle ?: kotlin.run {
            return@post
        }
        if (adapter == null) {
            adapter = BindingPagingDataAdapter<T>()
        }
        (adapter as? PagingDataAdapter<T, *>)?.submitData(lifecycle, list ?: PagingData.empty())
    }
}

@BindingAdapter("refreshLayout")
fun RecyclerView.setRefreshLayout(refreshLayout: SwipeRefreshLayout) {
    refreshLayout.setOnRefreshListener {
        (adapter as? PagingDataAdapter<*, *>)?.refresh()
    }

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val isRefreshEnabled = when (val lm = layoutManager) {
                is LinearLayoutManager -> lm.findFirstVisibleItemPosition() == 0
                else -> true
            }
            refreshLayout.isEnabled = isRefreshEnabled
        }
    })
}

@BindingAdapter("pagingStateHolder")
fun RecyclerView.setPagingStateHolder(pagingStateHolder: PagingStateHolder) {
    post {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            withTimeoutOrNull(5000L) {
                while (adapter == null || adapter !is PagingDataAdapter<*, *>) {
                    delay(200)
                }
                adapter as PagingDataAdapter<*, *>
            }?.let {
                pagingStateHolder.connectPagingState(it)
            }
        }
    }
}

@BindingAdapter("visible")
fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
