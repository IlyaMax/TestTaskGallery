package com.example.testtask.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtask.models.ImageItem
import com.example.testtask.models.LinkEntity
import com.example.testtask.models.LinkState
import com.example.testtask.models.Status
import com.example.testtask.repositories.LinksRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class LinksViewModel(application: Application) : AndroidViewModel(application) {
    private val ERR_TAG = "err_tag"
    private val linksRepository =
        LinksRepositoryImpl()
    val liveDataQueue = ArrayList<LiveData<Pair<Int, LinkState>>>()
    private var disposableUploadImage:Disposable? = null
    private var disposableGetAllLinks: Disposable? = null
    fun uploadImage(position: Int, imageItem: ImageItem): LiveData<Pair<Int, LinkState>> {
        val linkData = MutableLiveData<Pair<Int, LinkState>>()
        linkData.value = Pair(position,
            LinkState(
                Status.LOADING,
                null,
                null
            )
        )
        liveDataQueue.add(linkData)
        disposableUploadImage =
            linksRepository.uploadImage(getApplication(), position, imageItem.uri)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    linkData.value = Pair(position,
                        LinkState(
                            Status.SUCCESS,
                            it.second.link,
                            null
                        )
                    )
                }, {
                    Log.e(ERR_TAG,it.message!!)
                    linkData.value = Pair(position,
                        LinkState(
                            Status.ERROR,
                            null,
                            it
                        )
                    )
                })
        return linkData
    }

    fun getAllLinks(): LiveData<List<LinkEntity>> {
        val liveDataLinks = MutableLiveData<List<LinkEntity>>()
        disposableGetAllLinks = linksRepository.getAllLinks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ links ->
                liveDataLinks.value = links
            }, {
                it.printStackTrace()
            })
        return liveDataLinks
    }

    override fun onCleared() {
        super.onCleared()
        disposableGetAllLinks?.dispose()
        disposableUploadImage?.dispose()
    }
}