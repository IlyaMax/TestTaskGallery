package com.example.testtask.repositories

import android.content.Context
import android.net.Uri
import io.reactivex.Single

interface GalleryRepository {
    fun getImagesFromGallery(context: Context): Single<List<Uri>>
}
