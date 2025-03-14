package com.bso112.binder.example

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bso112.binder.binding.BindingPagingDataAdapter
import com.bso112.binder.binding.viewHolderBuilder
import com.bso112.binder.example.data.GetSectionUseCase
import com.bso112.binder.example.data.SectionUIModel
import com.bso112.binder.example.databinding.ActivityMainBinding
import com.bso112.binder.example.databinding.ItemSectionGridBinding
import com.bso112.binder.example.databinding.ItemSectionHorizontalBinding
import com.bso112.binder.example.databinding.ItemSectionVerticalBinding
import com.bso112.binder.example.util.forEachApply

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(GetSectionUseCase()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.rvSection.adapter = BindingPagingDataAdapter<SectionUIModel>(
            viewHolderBuilder<ItemSectionHorizontalBinding, SectionUIModel.Horizontal> { item ->
                item.onClickSection = {
                    viewModel.deleteSection(it)
                }
                item.productList.forEachApply {
                    onClickFavorite = {
                        viewModel.toggleProductLike(it.id)
                    }
                }
            },
            viewHolderBuilder<ItemSectionVerticalBinding, SectionUIModel.Vertical> { item ->
                item.onClickSection = {
                    viewModel.deleteSection(it)
                }
                item.productList.forEachApply {
                    onClickFavorite = {
                        viewModel.toggleProductLike(it.id)
                    }
                }
            },
            viewHolderBuilder<ItemSectionGridBinding, SectionUIModel.Grid> { item ->
                item.onClickSection = {
                    viewModel.deleteSection(it)
                }
                item.productList.forEachApply {
                    onClickFavorite = {
                        viewModel.toggleProductLike(it.id)
                    }
                }
            }
        )
    }
}