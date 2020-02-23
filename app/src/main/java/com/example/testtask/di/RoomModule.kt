package com.example.testtask.di

import android.app.Application
import androidx.room.Room
import com.example.testtask.room.AppDatabase
import com.example.testtask.room.LinksDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(application: Application) {
    private val appDatabase = Room.databaseBuilder(application, AppDatabase::class.java, "app_db")
        .fallbackToDestructiveMigration().build()

    @Provides
    fun provideLinksDao(): LinksDao {
        return appDatabase.linksDao()
    }
}