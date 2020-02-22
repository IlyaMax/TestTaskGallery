package com.example.testtask.di

import com.example.testtask.models.LinkState
import com.example.testtask.retrofit.ImgurApiService
import com.example.testtask.retrofit.LinkStateDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideImgurApiService(): ImgurApiService {
        val gson = GsonBuilder().registerTypeAdapter(
            LinkState::class.java,
            LinkStateDeserializer()
        ).create()
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor {
                val original = it.request()
                val request = original.newBuilder()
                    .header("Authorization", "Bearer db6b2d06fecb73876cc76fdd7fbe365902bbaf0d")
                    .build()
                it.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            )
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            )
            .baseUrl("https://api.imgur.com/")
            .build()
        return retrofit.create(ImgurApiService::class.java)
    }

}
