package com.example.testtask.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtask.repositories.GalleryRepositoryImpl
import com.example.testtask.models.ImageItem
import com.example.testtask.models.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val galleryRepository =
        GalleryRepositoryImpl()
    private val _galleryImagesData = MutableLiveData<List<ImageItem>>()
    private var disposableGetImagesFromGallery : Disposable? = null
    val galleryImagesData: LiveData<List<ImageItem>> get() = _galleryImagesData
    init {
        getImagesFromGallery()
    }

    private fun getImagesFromGallery() {
        disposableGetImagesFromGallery = galleryRepository.getImagesFromGallery(getApplication())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ paths ->
                _galleryImagesData.value =
                    paths.map {
                        ImageItem(
                            Status.NOT_LOADING,
                            it,
                            null
                        )
                    }
            }, {
                it.printStackTrace()
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposableGetImagesFromGallery?.dispose()
    }
}