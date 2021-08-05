package com.minwook.imagesearchdemo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.minwook.imagesearchdemo.data.SearchImage
import com.minwook.imagesearchdemo.network.ServerAPI
import com.minwook.imagesearchdemo.paging.ImageSearchPagingSource
import io.reactivex.Flowable
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val serverAPI: ServerAPI
) {

    fun getImageSearchResult(query: String, page: Int = 1): Flowable<PagingData<SearchImage>> {
        val searchDataSource = ImageSearchPagingSource(
            serverAPI,
            searchText = query
        )

        return Pager(
            config = PagingConfig(
                pageSize = 1,
                initialLoadSize = 30),
            pagingSourceFactory = { searchDataSource }
        ).flowable
    }
}
