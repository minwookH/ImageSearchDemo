package com.minwook.imagesearchdemo.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minwook.imagesearchdemo.data.SearchImage
import com.minwook.imagesearchdemo.databinding.ListItemImageBinding

class SearchListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var seartchItemlist: ArrayList<SearchImage> = arrayListOf()

    var onClickItem: ((SearchImage) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bind = ListItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(bind).apply {
            onClick = { data -> onClickItem?.invoke(data) }
        }
    }

    override fun getItemCount(): Int = seartchItemlist.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchViewHolder).bind(seartchItemlist[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setSearchList(searchList: ArrayList<SearchImage>) {
        seartchItemlist.addAll(searchList)
        notifyDataSetChanged()
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