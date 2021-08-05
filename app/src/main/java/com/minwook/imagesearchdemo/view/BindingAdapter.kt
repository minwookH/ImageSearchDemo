package com.minwook.imagesearchdemo.view

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.joda.time.DateTime

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("LoadImageUrl")
    fun bindLoadImageUrl(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .fitCenter()
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("dateTime")
    fun bindDateTime(view: TextView, date: String) {
        view.text = if (date.isNotEmpty()) {
            DateTime(date).toString("yyyy-MM-dd")
        } else {
            ""
        }
    }

}