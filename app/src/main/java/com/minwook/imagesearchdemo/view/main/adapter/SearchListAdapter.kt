package com.minwook.imagesearchdemo.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.minwook.imagesearchdemo.constants.Constants
import com.minwook.imagesearchdemo.data.SearchImage
import com.minwook.imagesearchdemo.databinding.ListItemImageBinding

class SearchListAdapter : PagingDataAdapter<SearchImage, SearchListAdapter.SearchViewHolder>(itemDiff) {

    var onClickItem: ((SearchImage) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val bind = ListItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(bind).apply {
            onClick = { data -> onClickItem?.invoke(data) }
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            Constants.SPAN_SIZE_IMAGE
        } else {
            Constants.SPAN_SIZE_STATE
        }
    }

    inner class SearchViewHolder(private var bind: ListItemImageBinding) : RecyclerView.ViewHolder(bind.root) {

        var onClick: ((SearchImage) -> Unit)? = null

        fun bind(data: SearchImage) {
            bind.searchImage = data
            bind.clListItem.setOnClickListener {
                onClick?.invoke(data)
            }
        }
    }
}

val itemDiff = object : DiffUtil.ItemCallback<SearchImage>() {
    override fun areItemsTheSame(oldItem: SearchImage, newItem: SearchImage): Boolean {
        return oldItem.thumbnailUrl == newItem.thumbnailUrl
    }

    override fun areContentsTheSame(oldItem: SearchImage, newItem: SearchImage): Boolean {
        return oldItem == newItem
    }
}
