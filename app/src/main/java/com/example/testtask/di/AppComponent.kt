package com.example.testtask.di

import com.example.testtask.viewmodels.GalleryViewModel
import com.example.testtask.viewmodels.GalleryViewModelFactory
import com.example.testtask.viewmodels.LinksViewModel
import com.example.testtask.viewmodels.LinksViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, RemoteModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(galleryViewModelFactory: GalleryViewModelFactory)
    fun inject(linksViewModelFactory: LinksViewModelFactory)
}