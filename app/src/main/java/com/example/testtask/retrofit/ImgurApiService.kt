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

    companion object {
        fun create(): ImgurApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor{
                    val original = it.request()
                    val request = original.newBuilder()
                        .header("Authorization", "Bearer db6b2d06fecb73876cc76fdd7fbe365902bbaf0d")
                        .build()
                    it.proceed(request)
                }
                .addInterceptor(interceptor)
                .build()
            val gson = GsonBuilder().registerTypeAdapter(LinkState::class.java,
                LinkStateDeserializer()
            )
                .create()
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
}