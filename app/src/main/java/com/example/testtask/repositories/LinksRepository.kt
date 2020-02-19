package com.example.testtask.repositories

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.testtask.models.LinkEntity
import com.example.testtask.models.LinkState
import io.reactivex.Single

interface LinksRepository {
    fun uploadImage(context: Context, position: Int, uri: Uri): Single<Pair<Int, LinkState>>
    fun getAllLinks(): LiveData<List<LinkEntity>>
}
