package com.minwook.imagesearchdemo.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import com.minwook.imagesearchdemo.constants.Constants
import com.minwook.imagesearchdemo.data.SearchImage
import com.minwook.imagesearchdemo.databinding.ActivityMainBinding
import com.minwook.imagesearchdemo.util.ViewUtils
import com.minwook.imagesearchdemo.util.gone
import com.minwook.imagesearchdemo.util.hideKeyboard
import com.minwook.imagesearchdemo.util.visible
import com.minwook.imagesearchdemo.view.detail.DetailActivity
import com.minwook.imagesearchdemo.view.main.adapter.SearchListAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchListAdapter: SearchListAdapter
    private val mainViewModel by viewModels<MainViewModel>()
    private val textChangeTime = 1L

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
                .debounce(textChangeTime, TimeUnit.SECONDS)
                .filter { !it.isNullOrBlank() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    etSearch.hideKeyboard()
                    etSearch.clearFocus()
                    mainViewModel.loadImageSearchList(it.toString())
                }
            searchListAdapter = SearchListAdapter().apply {
                onClickItem = {
                    moveDetailActivity(it)
                }
            }
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
            rvImageList.layoutManager = GridLayoutManager(this@MainActivity, Constants.SPAN_SIZE_IMAGE).apply {
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
        mainViewModel.searchList.observe(
            this,
            {
                searchListAdapter.submitData(lifecycle, it)
            }
        )

        mainViewModel.error.observe(
            this,
            {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun moveDetailActivity(data: SearchImage) {
        Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_DATA, data)
            startActivity(this)
        }
    }
}
