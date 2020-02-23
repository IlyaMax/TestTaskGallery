package com.example.testtask.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.App
import com.example.testtask.repositories.GalleryRepository
import javax.inject.Inject

class GalleryViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    @Inject
    lateinit var galleryRepository: GalleryRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        App.appComponent.inject(this)
        if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            return GalleryViewModel(application,galleryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}