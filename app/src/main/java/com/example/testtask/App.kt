package com.example.testtask;

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.room.Room
import com.example.testtask.room.AppDatabase
import io.reactivex.plugins.RxJavaPlugins


class App : Application() {
    companion object {
        lateinit var database: AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration().build()
        RxJavaPlugins.setErrorHandler { throwable: Throwable? -> Log.e("rxerror",throwable?.message!!)}
    }

}
