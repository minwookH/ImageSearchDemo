package com.minwook.imagesearchdemo.di

import com.minwook.imagesearchdemo.network.ServerAPI
import com.minwook.imagesearchdemo.repository.SearchRepository
import com.minwook.imagesearchdemo.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideSearchRepository(serverAPI: ServerAPI): SearchRepository = SearchRepositoryImpl(serverAPI)
}