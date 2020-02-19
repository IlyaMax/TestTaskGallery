package com.example.testtask.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testtask.models.LinkEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LinksDao {

    @Query("SELECT * FROM link")
    fun getLinks(): LiveData<List<LinkEntity>>

    @Insert
    fun insertLink(link: LinkEntity): Completable
}
