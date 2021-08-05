package com.minwook.imagesearchdemo.network

import com.minwook.imagesearchdemo.constants.Constants
import com.minwook.imagesearchdemo.data.ImageSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerAPI {

    @GET("/v2/search/image")
    fun getImageSearchResult(@Query("query") query: String,
                             @Query("sort") sort: String = "accuracy",
                             @Query("page") page: Int = Constants.INIT_PAGE,
                             @Query("size") size: Int = Constants.PAGE_SIZE): Single<ImageSearchResponse>
}
