package com.example.testtask.di

import com.example.testtask.viewmodels.GalleryViewModel
import com.example.testtask.viewmodels.LinksViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RemoteModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(galleryViewModel: GalleryViewModel)
    fun inject(linksViewModel: LinksViewModel)
}