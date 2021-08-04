package com.minwook.imagesearchdemo.view

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.joda.time.DateTime

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("bindLoadListImageUrl")
    fun bindLoadListImageUrl(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .fitCenter()
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("dateTime")
    fun bindDateTime(view: TextView, date: String) {
        view.text = DateTime(date).toString("yyyy년 MM월 dd일")
    }

    @JvmStatic
    @BindingAdapter("dateTimeHHmm")
    fun bindDateTimeHHmm(view: TextView, date: String) {
        view.text = DateTime(date).toString("yyyy년 MM월 dd일 aa hh시 mm분")
    }

}