package com.minwook.imagesearchdemo.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minwook.imagesearchdemo.databinding.ItemGridLoadStateBinding
import com.minwook.imagesearchdemo.util.gone
import com.minwook.imagesearchdemo.util.visible

class SearchStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemGridLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }
}

class LoadStateViewHolder(private val bind: ItemGridLoadStateBinding) : RecyclerView.ViewHolder(bind.root) {

    fun bind(loadState: LoadState, retry: () -> Unit) {
        with(bind) {
            when (loadState) {
                is LoadState.Error -> {
                    pbLoading.gone()
                    btRetry.visible()
                }

                is LoadState.Loading -> {
                    pbLoading.visible()
                    btRetry.gone()
                }
                is LoadState.NotLoading -> { }
            }

            btRetry.setOnClickListener {
                retry.invoke()
                btRetry.gone()
            }
        }
    }
}
