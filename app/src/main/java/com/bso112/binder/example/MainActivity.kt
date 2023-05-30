package com.bso112.binder.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bso112.binder.example.binding.BindingPagingDataAdapter
import com.bso112.binder.example.data.Section
import com.bso112.binder.example.databinding.ActivityMainBinding
import com.bso112.binder.example.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionAdapter = BindingPagingDataAdapter<Section>()
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.rvSection.adapter = sectionAdapter

        binding.layoutRefresh.setOnRefreshListener {
            sectionAdapter.refresh()
        }

        repeatOnStarted {
            viewModel.connectPagingState(sectionAdapter)
        }

        repeatOnStarted {
            viewModel.sectionList.collect {
                sectionAdapter.submitData(it)
            }
        }
    }
}