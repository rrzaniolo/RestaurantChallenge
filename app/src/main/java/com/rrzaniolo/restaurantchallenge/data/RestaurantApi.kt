package com.rrzaniolo.restaurantchallenge.data

import com.rrzaniolo.restaurantchallenge.data.models.RestaurantListResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */

/* Configuring code for when it uses an api instead of local JsonFile. */
interface RestaurantApi {

    @GET("movie/upcoming")
    fun getRestaurants(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int = 1
    ): Flowable<RestaurantListResponse>
}