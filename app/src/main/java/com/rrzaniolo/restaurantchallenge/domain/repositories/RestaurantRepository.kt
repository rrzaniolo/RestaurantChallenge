package com.rrzaniolo.restaurantchallenge.domain.repositories

import com.rrzaniolo.restaurantchallenge.data.models.RestaurantListResponse
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
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
     * @return a list of RestaurantEntity**/
    fun getRestaurants(): Flowable<RestaurantListResponse>

    /**
     * Save a restaurant locally.
     *
     * @param entity the restaurant to be saved**/
    fun saveRestaurant(entity: RestaurantEntity): Completable

    /**
     * Get a restaurant that was locally saved by its Id.
     *
     * @param name the name of the restaurant.
     * @return A RestaurantEntity
     * **/
    fun getRestaurantByName(name: String?): Single<RestaurantEntity>

    /**
     * Delete a restaurant that was locally saved.
     *
     * @param entity the restaurant to be deleted.
     * **/
    fun deleteRestaurant(entity: RestaurantEntity): Completable

    /**
     * Get a list with all the restaurants that have been locally saved.
     *
     * @return a list of RestaurantEntity**/
    fun getRestaurantsLocally(): Flowable<ArrayList<RestaurantEntity>>
}