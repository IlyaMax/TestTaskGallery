package com.example.testtask.repositories

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.LiveData
import com.example.testtask.*
import com.example.testtask.models.LinkEntity
import com.example.testtask.models.LinkState
import com.example.testtask.models.Status
import com.example.testtask.retrofit.ImgurApiService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LinksRepositoryImpl : LinksRepository {
    private val scheduler = Schedulers.single()
    private val apiService =
        ImgurApiService.create()

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
        return apiService.uploadImage(Base64.encodeToString(bytes, Base64.DEFAULT))
            .flatMap { linkState: LinkState ->
                if (linkState.link != null)
                    App.database.linksDao().insertLink(
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

    override fun getAllLinks(): LiveData<List<LinkEntity>> = App.database.linksDao().getLinks()
}
