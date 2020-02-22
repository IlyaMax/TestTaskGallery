package com.example.testtask.di

import com.example.testtask.repositories.GalleryRepository
import com.example.testtask.repositories.GalleryRepositoryImpl
import com.example.testtask.repositories.LinksRepository
import com.example.testtask.repositories.LinksRepositoryImpl
import com.example.testtask.retrofit.ImgurApiService
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideLinksRepository(imgurApiService: ImgurApiService): LinksRepository {
        return LinksRepositoryImpl(imgurApiService)
    }
    @Provides
    fun provideGalleryRepository(): GalleryRepository {
        return GalleryRepositoryImpl()
    }
}