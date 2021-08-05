package com.minwook.imagesearchdemo.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.minwook.imagesearchdemo.data.ImageSearchResponse
import com.minwook.imagesearchdemo.data.SearchImage
import com.minwook.imagesearchdemo.network.ServerAPI
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ImageSearchPagingSource(
    private val searchImageService: ServerAPI,
    private val searchText: String
) : RxPagingSource<Int, SearchImage>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, SearchImage>> {
        val currentPage = params.key ?: 1

        return searchImageService.getImageSearchResult(
            searchText,
            page = currentPage
        )
            .subscribeOn(Schedulers.computation())
            .map { response ->
                toLoadResult(response, currentPage)
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchImage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun toLoadResult(response: ImageSearchResponse, currentPage: Int): LoadResult<Int, SearchImage> {
        return LoadResult.Page(
            data = response.documents,
            prevKey = null,
            nextKey = if (response.meta.isEnd) null else currentPage + 1
        )
    }
}
