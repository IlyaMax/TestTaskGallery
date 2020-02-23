package com.example.testtask.repositories

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.LiveData
import com.example.testtask.*
import com.example.testtask.di.RemoteModule
import com.example.testtask.models.LinkEntity
import com.example.testtask.models.LinkState
import com.example.testtask.models.Status
import com.example.testtask.retrofit.ImgurApiService
import com.example.testtask.room.LinksDao
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LinksRepositoryImpl(private val imgurApiService: ImgurApiService,private val linksDao: LinksDao) : LinksRepository {
    private val scheduler = Schedulers.single()

    override fun uploadImage(
        context: Context,
        position: Int,
        uri: Uri
    ): Single<Pair<Int, LinkState>> {
        val bytes = context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }
            ?: return Single.just(
                Pair(
                    position,
                    LinkState(
                        Status.ERROR,
                        null,
                        Throwable("Error with reading image file")
                    )
                )
            )
        return imgurApiService.uploadImage(Base64.encodeToString(bytes, Base64.DEFAULT))
            .flatMap { linkState: LinkState ->
                if (linkState.link != null)
                    linksDao.insertLink(
                        LinkEntity(linkState.link)
                    ).andThen(
                        Single.just(
                            Pair(position, linkState)
                        )
                    )
                else // в LinkState должен быть Error
                    Single.just(Pair(position, linkState))
            }
            .subscribeOn(scheduler)
    }

    override fun getAllLinks(): LiveData<List<LinkEntity>> = linksDao.getLinks()
}
