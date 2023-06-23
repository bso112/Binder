package com.bso112.binder.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bso112.binder.adapter.BindingPagingDataAdapter
import com.bso112.binder.example.data.SectionUIModel
import com.bso112.binder.example.databinding.ActivityMainBinding
import com.bso112.binder.example.databinding.ItemSectionGridBinding
import com.bso112.binder.example.databinding.ItemSectionHorizontalBinding
import com.bso112.binder.example.databinding.ItemSectionVerticalBinding
import com.bso112.binder.buildBinder
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
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.rvSection.adapter = BindingPagingDataAdapter<SectionUIModel>(
            buildBinder<ItemSectionHorizontalBinding, SectionUIModel.Horizontal> { item ->
                item.onClickSection = {
                    viewModel.deleteSection(it)
                }
            },
            buildBinder<ItemSectionVerticalBinding, SectionUIModel.Vertical> { item ->
                item.onClickSection = {
                    viewModel.deleteSection(it)
                }
            },
            buildBinder<ItemSectionGridBinding, SectionUIModel.Grid> { item ->
                item.onClickSection = {
                    viewModel.deleteSection(it)
                }
            }
        )
    }
}