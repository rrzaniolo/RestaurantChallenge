package com.rrzaniolo.restaurantchallenge.domain.usecases

import com.rrzaniolo.restaurantchallenge.data.models.RestaurantListResponse
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import com.rrzaniolo.restaurantchallenge.domain.repositories.RestaurantRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
class RestaurantListUseCase(private val repository: RestaurantRepository) {

    fun getRestaurants():Flowable<RestaurantListResponse> = repository.getRestaurants()

    fun saveRestaurant(entity: RestaurantEntity): Completable = repository.saveRestaurant(entity)
    fun deleteRestaurant(entity: RestaurantEntity): Completable = repository.deleteRestaurant(entity)
    fun getRestaurantByName(name: String): Single<RestaurantEntity> = repository.getRestaurantByName(name)

    fun getFavoriteRestaurants(): Flowable<ArrayList<RestaurantEntity>> = repository.getRestaurantsLocally()
}