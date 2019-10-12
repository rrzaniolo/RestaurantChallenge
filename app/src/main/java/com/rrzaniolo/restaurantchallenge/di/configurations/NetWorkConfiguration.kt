package com.rrzaniolo.restaurantchallenge.di.configurations

import com.rrzaniolo.restaurantchallenge.BuildConfig
import com.rrzaniolo.restaurantchallenge.data.RestaurantApi
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Rodrigo Rodrigues Zaniolo on 9/25/2019.
 * All rights reserved.
 */

/* Configuring code for when it uses an api instead of local JsonFile. */

fun provideOkHttpClientBuilder(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient {
    val builder = OkHttpClient.Builder()
    interceptors.forEach { builder.addInterceptor(it) }
    if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        builder.addInterceptor(httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        })
    }
    return builder.build()
}

fun provideInterceptor(): Set<@JvmSuppressWildcards Interceptor> = setOf(Interceptor { chain ->
    val request = chain.request()

    chain.proceed(request)
})

fun provideMoshi(): Moshi = Moshi.Builder().build()

fun provideRestaurantApi():
        RestaurantApi {
    return Retrofit.Builder()
        .client(provideOkHttpClientBuilder(provideInterceptor()))
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .build()
        .create(RestaurantApi::class.java)
}