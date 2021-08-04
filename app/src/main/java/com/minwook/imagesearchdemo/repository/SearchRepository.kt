package com.minwook.imagesearchdemo.repository

import com.minwook.imagesearchdemo.data.ImageSearchResponse
import com.minwook.imagesearchdemo.network.ServerAPI
import io.reactivex.Single
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val serverAPI: ServerAPI
) {

    fun getImageSearchResult(query: String, page: Int): Single<ImageSearchResponse> {
        return serverAPI.getImageSearchResult(query, page = page)
    }
}
