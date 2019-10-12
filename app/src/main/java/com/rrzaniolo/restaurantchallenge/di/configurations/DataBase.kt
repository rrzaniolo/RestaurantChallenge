package com.rrzaniolo.restaurantchallenge.di.configurations

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rrzaniolo.restaurantchallenge.data.dao.RestaurantDao
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
@Database(entities = [RestaurantEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
}