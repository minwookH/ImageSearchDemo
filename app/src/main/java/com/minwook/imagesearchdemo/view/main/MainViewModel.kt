package com.minwook.imagesearchdemo.view.main

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.minwook.imagesearchdemo.data.ImageSearchResponse
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

    private val _searchList = MutableLiveData<PagingData<SearchImage>>()
    val searchList: LiveData<PagingData<SearchImage>>
        get() = _searchList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun loadImageSearchList(text: String) {
        searchRepository.getImageSearchResult(text)
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _searchList.value = it
            }, {
                _error.value = it.localizedMessage
            })
            .addTo(compositeDisposable)
    }
}