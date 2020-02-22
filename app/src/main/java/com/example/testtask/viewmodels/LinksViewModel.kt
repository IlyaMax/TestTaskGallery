package com.example.testtask.viewmodels

import android.app.Application
import android.util.Log
import android.util.SparseArray
import androidx.core.util.set
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtask.App
import com.example.testtask.models.ImageItem
import com.example.testtask.models.LinkEntity
import com.example.testtask.models.LinkState
import com.example.testtask.models.Status
import com.example.testtask.repositories.GalleryRepository
import com.example.testtask.repositories.LinksRepository
import com.example.testtask.repositories.LinksRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class LinksViewModel(application: Application) : AndroidViewModel(application) {
    private val ERR_TAG = "err_tag"
    @Inject
    lateinit var linksRepository: LinksRepository
    private val _linkStates = MutableLiveData<SparseArray<LinkState>>()
    private var disposableUploadImage: Disposable? = null
    val linkStates: LiveData<SparseArray<LinkState>>
        get() = _linkStates
    init{
        App.appComponent.inject(this)
    }

    fun uploadImage(position: Int, imageItem: ImageItem) {
        if (_linkStates.value == null) _linkStates.value = SparseArray()
        _linkStates.value!![position] =
            LinkState(
                Status.LOADING,
                null,
                null
            )
        _linkStates.value = _linkStates.value
        disposableUploadImage =
            linksRepository.uploadImage(getApplication(), position, imageItem.uri)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _linkStates.value!![position] =
                        LinkState(
                            Status.SUCCESS,
                            it.second.link,
                            null
                        )
                    _linkStates.value = _linkStates.value
                }, {
                    Log.e(ERR_TAG, it.message!!)
                    _linkStates.value!![position] =
                        LinkState(
                            Status.ERROR,
                            null,
                            it
                        )
                    _linkStates.value = _linkStates.value
                })
    }

    fun getAllLinks(): LiveData<List<LinkEntity>> {
        return linksRepository.getAllLinks()
    }

    override fun onCleared() {
        super.onCleared()
        disposableUploadImage?.dispose()
    }
}