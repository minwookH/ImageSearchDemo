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

class SearchRepositoryImpl @Inject constructor (private val serverAPI: ServerAPI): SearchRepository {
    override fun getImageSearchResult(query: String, page: Int): Flowable<PagingData<SearchImage>> {
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

interface SearchRepository {
    fun getImageSearchResult(query: String, page: Int = 1): Flowable<PagingData<SearchImage>>
}
