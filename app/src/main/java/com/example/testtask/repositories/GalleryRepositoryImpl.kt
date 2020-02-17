package com.example.testtask.repositories

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.testtask.repositories.GalleryRepository
import io.reactivex.Single


class GalleryRepositoryImpl :
    GalleryRepository {
    val TAG = "gallery_log"
    override fun getImagesFromGallery(context: Context): Single<List<Uri>> {
        return Single.create {
            val list: MutableList<Uri> = mutableListOf()
            val contentResolver: ContentResolver = context.contentResolver
            val imagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val cursor = contentResolver.query(imagesUri, null, null, null, null)
            try {
                cursor!!.moveToFirst()
                val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                while (cursor.moveToNext()) {
                    val currId = cursor.getLong(idColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        imagesUri,
                        currId
                    )
                    list.add(contentUri)
                }
            } catch (e: NullPointerException) {
                it.onError(Throwable("Error getting gallery images"))
            } finally {
                cursor?.close()
            }
            Log.d(TAG, list.toString())
            it.onSuccess(list)
        }
    }
}
