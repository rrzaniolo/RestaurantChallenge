package com.rrzaniolo.restaurantchallenge.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Rodrigo Rodrigues Zaniolo on 9/25/2019.
 * All rights reserved.
 */
@Dao
interface RestaurantDao {

    /**
     * Save a restaurant locally.
     *
     * @param entity the restaurant to be saved.
     * **/
    @Insert fun saveRestaurant(entity: RestaurantEntity): Completable

    /**
     * Get a restaurant that was locally saved by it's id.
     *
     * @param name the restaurant name.
     * @return a RestaurantEntity
     * **/
    @Query("SELECT * FROM restaurantentity WHERE name = :name") fun getRestaurantByName(name: String?): Single<RestaurantEntity>

    /**
     * Delete a restaurant tha has been locally saved
     *
     * @param entity the restaurant to be deleted.
     * **/
    @Delete fun deleteRestaurant(entity: RestaurantEntity): Completable

    /**
     * Gets a list of all the locally saved restaurants
     *
     * @return a list of RestaurantResponse.
     * **/
    @Query("SELECT * FROM restaurantentity") fun getRestaurants(): Flowable<ArrayList<RestaurantEntity>>

}