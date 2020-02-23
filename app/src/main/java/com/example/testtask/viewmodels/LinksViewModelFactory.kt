package com.example.testtask.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.App
import com.example.testtask.repositories.GalleryRepository
import com.example.testtask.repositories.LinksRepository
import javax.inject.Inject

class LinksViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    @Inject
    lateinit var linksRepository: LinksRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        App.appComponent.inject(this)
        if (modelClass.isAssignableFrom(LinksViewModel::class.java)) {
            return LinksViewModel(application,linksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}