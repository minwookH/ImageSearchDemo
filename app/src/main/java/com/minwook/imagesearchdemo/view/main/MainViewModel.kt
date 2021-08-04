package com.minwook.imagesearchdemo.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minwook.imagesearchdemo.data.SearchImage
import com.minwook.imagesearchdemo.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchList = MutableLiveData<ArrayList<SearchImage>>()
    val searchList: LiveData<ArrayList<SearchImage>>
        get() = _searchList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val compositeDisposable = CompositeDisposable()

    fun loadImageSearchList(text: String, page: Int = 1) {
        /*compositeDisposable.add(
            searchRepository.getImageSearchResult(text, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({


                    _searchList.value = blogList
                }, {
                    Log.e("search", "loadSearchList error : ${it.localizedMessage}")
                }).addTo(compositeDisposable)
        )*/

        Log.d("testest", "loadImageSearchList : $text")
        searchRepository.getImageSearchResult(text, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                Log.d("testest", "loadImageSearchList result : ${it.documents.isEmpty()}")
                if (it.documents.isEmpty()) {

                } else {
                    _searchList.postValue(it.documents)
                }
            }, {
                Log.e("search", "loadSearchList error : ${it.localizedMessage}")
            })
            .addTo(compositeDisposable)
    }
    /*
        private fun loadCafeSearchList(text: String, page: Int = 1) {
            compositeDisposable.add(
                searchRepository.getCafeSearchResult(text, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val cafeList = arrayListOf<SearchItem>()

                        it.documents.forEach {
                            cafeList.add(SearchItem(it.title, it.contents, it.url, it.cafename, it.thumbnail, it.datetime, "cafe"))
                        }

                        _searchList.value = cafeList
                    }, {
                        Log.e("search", "loadSearchList error : ${it.localizedMessage}")
                    })
            )
        }

        fun getSearch(text: String, type: String, page: Int = 1) {
            when (type) {
                "Blog" -> {
                    loadBlogSearchList(text, page)
                }
                "Cafe" -> {
                    loadCafeSearchList(text, page)
                }
                else -> {
                    loadSearchList(text, page)
                }
            }
        }

        fun getSearchHistory() {
            compositeDisposable.add(
                searchRepository.getSearchHistory()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val map = it.map { data -> data.searchText }
                        _searchHistoryList.postValue(map)
                    }, {
                        _searchHistoryList.postValue(listOf())
                        Log.e("search", "loadSearchList error : ${it.localizedMessage}")
                    })
            )
        }

        fun insertSearchHistory(text: String) {
            compositeDisposable.add(
                Single.just(text).subscribeOn(Schedulers.io())
                    .subscribe({
                        searchRepository.insertSearchHistory(it)
                    }, {
                        Log.e("search", it.localizedMessage)
                    })
            )
        }

        fun combineItemList(firstList: ArrayList<Blog>, secondList: ArrayList<Cafe>): ArrayList<SearchItem> {
            val searchList = arrayListOf<SearchItem>()

            firstList.forEach {
                searchList.add(SearchItem(it.title, it.contents, it.url, it.blogname, it.thumbnail, it.datetime, "blog"))
            }

            secondList.forEach {
                searchList.add(SearchItem(it.title, it.contents, it.url, it.cafename, it.thumbnail, it.datetime, "cafe"))
            }

            return searchList
        }*/

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}