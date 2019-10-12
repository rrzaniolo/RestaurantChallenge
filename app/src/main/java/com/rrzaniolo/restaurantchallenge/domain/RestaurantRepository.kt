package com.rrzaniolo.restaurantchallenge.domain

import com.rrzaniolo.restaurantchallenge.data.models.RestaurantResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
interface RestaurantRepository {
    /**
     * Get the restaurant list from the api.
     *
     * @return a list of MovieResponse**/
    fun getRestaurants(): Flowable<RestaurantResponse>

    /**
     * Save a restaurant locally.
     *
     * @param restaurantResponse the movie to be saved**/
    fun saveRestaurant(restaurantResponse: RestaurantResponse): Completable

    /**
     * Get a restaurant that was locally saved by its Id.
     *
     * @param name the name of the restaurant.
     * @return A RestaurantResponse
     * **/
    fun getRestaurantById(name: String?): Single<RestaurantResponse>

    /**
     * Delete a restaurant that was locally saved.
     *
     * @param restaurantResponse the movie to be deleted.
     * **/
    fun deleteRestaurant(restaurantResponse: RestaurantResponse): Completable

    /**
     * Get a list with all the restaurants that have been locally saved.
     *
     * @return a list of RestaurantResponse**/
    fun getRestaurantsLocally(): Flowable<List<RestaurantResponse>>
}