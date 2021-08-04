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
    @SerializedName("thumbnail_url")
    var thumbnailUrl: String,
    @SerializedName("image_url")
    var imageUrl: String,
    var width: Int,
    var height: Int,
    @SerializedName("display_sitename")
    var displaySitename: String,
    @SerializedName("doc_url")
    var docUrl: String,
    var datetime: String
) : Parcelable
