package com.minwook.imagesearchdemo.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import com.minwook.imagesearchdemo.databinding.ActivityMainBinding
import com.minwook.imagesearchdemo.util.ViewUtils
import com.minwook.imagesearchdemo.util.gone
import com.minwook.imagesearchdemo.util.hideKeyboard
import com.minwook.imagesearchdemo.util.visible
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchListAdapter: SearchListAdapter
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObserve()
    }

    private fun initView() {
        binding.apply {
            etSearch.textChanges()
                .debounce(1, TimeUnit.SECONDS)
                .filter {
                    !it.isNullOrBlank()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    etSearch.hideKeyboard()
                    etSearch.clearFocus()
                    mainViewModel.loadImageSearchList(it.toString())
                }

            searchListAdapter = SearchListAdapter()
            searchListAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && searchListAdapter.itemCount < 1) {
                    rvImageList.gone()
                    tvEmpty.visible()
                } else {
                    rvImageList.visible()
                    tvEmpty.gone()
                }
            }

            rvImageList.adapter = searchListAdapter.withLoadStateFooter(SearchStateAdapter { searchListAdapter.retry() })
            rvImageList.setHasFixedSize(true)
            rvImageList.layoutManager = GridLayoutManager(this@MainActivity, 3).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return searchListAdapter.getItemViewType(position)
                    }
                }
            }
            rvImageList.addItemDecoration(ImageItemDecoration(ViewUtils.dpToPx(6f, this@MainActivity)))
        }
    }

    private fun initObserve() {
        mainViewModel.searchList.observe(this, {
            searchListAdapter.submitData(lifecycle, it)
        })

        mainViewModel.error.observe(this, {
        })
    }
}