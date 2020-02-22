package com.example.testtask.retrofit

import com.example.testtask.models.LinkState
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ImgurApiService {
    @FormUrlEncoded
    @POST("3/upload")
    fun uploadImage(@Field("image") image: String):
            Single<LinkState>
}