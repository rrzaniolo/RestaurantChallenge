package com.rrzaniolo.restaurantchallenge.di.configurations

import android.content.Context
import androidx.room.Room

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
fun provideDataBaseBuilder(context: Context) = Room.databaseBuilder(
    context,
    Database::class.java,
    "movieApiChallenge.db"
).build()