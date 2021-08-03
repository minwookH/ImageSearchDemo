package com.minwook.imagesearchdemo.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ImageSearchResponse(
    var meta: Meta,
    var documents: ArrayList<SearchImage>
)

data class Meta(
    @SerializedName("total_count")
    var totalCount: Int,
    @SerializedName("pageable_count")
    var pageableCount: Int,
    @SerializedName("is_end")
    var isEnd: Boolean,
)

@Parcelize
data class SearchImage(
    var collection: String,
    var thumbnail_url: String,
    var image_url: String,
    var width: Int,
    var height: Int,
    var display_sitename: String,
    var doc_url: String,
    var datetime: String
) : Parcelable
