package com.example.testtask

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.util.Log
import androidx.room.Room
import com.example.testtask.di.AppComponent
import com.example.testtask.di.DaggerAppComponent
import com.example.testtask.di.RoomModule
import com.example.testtask.room.AppDatabase
import io.reactivex.plugins.RxJavaPlugins


class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
        var isLarge: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        isLarge = ((resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
        appComponent = DaggerAppComponent.builder().roomModule(RoomModule(this)).build()
        RxJavaPlugins.setErrorHandler { throwable: Throwable? ->
            Log.e(
                "rxerror",
                throwable?.message!!
            )
        }
    }

}
