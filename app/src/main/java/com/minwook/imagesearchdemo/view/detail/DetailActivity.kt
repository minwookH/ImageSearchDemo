package com.minwook.imagesearchdemo.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minwook.imagesearchdemo.data.SearchImage
import com.minwook.imagesearchdemo.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

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
