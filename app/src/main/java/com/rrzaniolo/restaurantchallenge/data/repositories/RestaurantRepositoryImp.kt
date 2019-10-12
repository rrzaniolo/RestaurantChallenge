package com.rrzaniolo.restaurantchallenge.data.repositories

import com.rrzaniolo.restaurantchallenge.data.models.RestaurantListResponse
import com.rrzaniolo.restaurantchallenge.di.configurations.Database
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import com.rrzaniolo.restaurantchallenge.domain.repositories.RestaurantRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
class RestaurantRepositoryImp( private val dataBase: Database):
    RestaurantRepository {
    override fun getRestaurants(): Flowable<RestaurantListResponse> {
        TODO("not implemented") //Add restaurant json file to project and read data from it (since we do not have an api yet)
    }

    override fun saveRestaurant(entity: RestaurantEntity): Completable {
        return dataBase
            .restaurantDao()
            .saveRestaurant(entity)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
    }

    override fun getRestaurantByName(name: String?): Single<RestaurantEntity> {
        return dataBase
            .restaurantDao()
            .getRestaurantByName(name)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
    }

    override fun deleteRestaurant(entity: RestaurantEntity): Completable {
        return dataBase
            .restaurantDao()
            .deleteRestaurant(entity)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
    }

    override fun getRestaurantsLocally(): Flowable<ArrayList<RestaurantEntity>> {
        return dataBase
            .restaurantDao()
            .getRestaurants()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}