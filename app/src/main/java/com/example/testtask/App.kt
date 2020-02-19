package com.example.testtask

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.util.Log
import androidx.room.Room
import com.example.testtask.room.AppDatabase
import io.reactivex.plugins.RxJavaPlugins


class App : Application() {
    companion object {
        lateinit var database: AppDatabase
        var isLarge: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        isLarge = ((resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration().build()
        RxJavaPlugins.setErrorHandler { throwable: Throwable? ->
            Log.e(
                "rxerror",
                throwable?.message!!
            )
        }
    }

}
