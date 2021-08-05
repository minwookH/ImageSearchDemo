package com.minwook.imagesearchdemo.view.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import com.minwook.imagesearchdemo.data.SearchImage
import com.minwook.imagesearchdemo.databinding.ActivityDetailBinding
import com.minwook.imagesearchdemo.databinding.ActivityMainBinding
import com.minwook.imagesearchdemo.util.ViewUtils
import com.minwook.imagesearchdemo.util.gone
import com.minwook.imagesearchdemo.util.hideKeyboard
import com.minwook.imagesearchdemo.util.visible
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_DATA = "EXTRA_DATA"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            if (intent.hasExtra(EXTRA_DATA)) {
                searchImage = intent.getParcelableExtra<SearchImage>(EXTRA_DATA)
            }
        }
    }
}