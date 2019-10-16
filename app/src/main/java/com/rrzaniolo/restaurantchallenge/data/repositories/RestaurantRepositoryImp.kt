package com.rrzaniolo.restaurantchallenge.data.repositories

import com.rrzaniolo.restaurantchallenge.data.models.RestaurantListResponse
import com.rrzaniolo.restaurantchallenge.di.configurations.Database
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import com.rrzaniolo.restaurantchallenge.domain.repositories.RestaurantRepository
import com.squareup.moshi.Moshi
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
class RestaurantRepositoryImp(private val moshi: Moshi, private val dataBase: Database):
    RestaurantRepository {
    private fun readJsonFile(jsonInputStream: InputStream): String {
        return ByteArrayOutputStream().apply {
            try {
                jsonInputStream.use { input ->
                    this.use { output ->
                        input.copyTo(output)
                    }
                }

                this.close()
                jsonInputStream.close()
            } catch (exception: IOException) {}
        }.toString()
    }

    override fun getRestaurants(): Flowable<RestaurantListResponse> {
        this::class.java.classLoader?.getResourceAsStream("assets/restaurants.json")?.let {
            return Flowable.just(
                moshi.adapter(RestaurantListResponse::class.java).fromJson(readJsonFile(it))
            )
        }?:run{
            return Flowable.just(RestaurantListResponse(listOf()))
        }
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

    override fun getRestaurantsLocally(): Flowable<List<RestaurantEntity>> {
        return dataBase
            .restaurantDao()
            .getRestaurants()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}