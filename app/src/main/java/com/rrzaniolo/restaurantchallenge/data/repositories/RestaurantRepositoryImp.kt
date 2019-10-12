package com.rrzaniolo.restaurantchallenge.data.repositories

import com.rrzaniolo.restaurantchallenge.data.models.RestaurantResponse
import com.rrzaniolo.restaurantchallenge.di.configurations.Database
import com.rrzaniolo.restaurantchallenge.domain.RestaurantRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
class RestaurantRepositoryImp( private val dataBase: Database): RestaurantRepository{
    override fun getRestaurants(): Flowable<RestaurantResponse> {
        TODO("not implemented") //Add restaurant json file to project and read data from it (since we do not have an api yet)
    }

    override fun saveRestaurant(restaurantResponse: RestaurantResponse): Completable {
        return dataBase
            .restaurantDao()
            .saveRestaurant(restaurantResponse)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
    }

    override fun getRestaurantById(name: String?): Single<RestaurantResponse> {
        return dataBase
            .restaurantDao()
            .getRestaurantByName(name)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
    }

    override fun deleteRestaurant(restaurantResponse: RestaurantResponse): Completable {
        return dataBase
            .restaurantDao()
            .deleteRestaurant(restaurantResponse)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
    }

    override fun getRestaurantsLocally(): Flowable<List<RestaurantResponse>> {
        return dataBase
            .restaurantDao()
            .getRestaurants()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}