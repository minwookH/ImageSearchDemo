package com.minwook.imagesearchdemo.view.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import com.minwook.imagesearchdemo.databinding.ActivityMainBinding
import com.minwook.imagesearchdemo.util.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {

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
                    mainViewModel.loadImageSearchList(it.toString())
                }

            searchListAdapter = SearchListAdapter()
            rvImageList.adapter = searchListAdapter
            rvImageList.setHasFixedSize(true)
            rvImageList.layoutManager = GridLayoutManager(this@MainActivity2, 3)
            rvImageList.addItemDecoration(ImageItemDecoration(ViewUtils.dpToPx(6f, this@MainActivity2)))
        }
    }

    private fun initObserve() {
        mainViewModel.searchList.observe(this, {
            searchListAdapter.setSearchList(it)
        })

        mainViewModel.error.observe(this, {
            binding.rvImageList.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        })
    }
}