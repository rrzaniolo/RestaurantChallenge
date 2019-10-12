package com.rrzaniolo.restaurantchallenge

import android.app.Application
import com.rrzaniolo.restaurantchallenge.di.modules.dataBaseModule
import com.rrzaniolo.restaurantchallenge.di.modules.netWorkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Rodrigo Rodrigues Zaniolo on 9/25/2019.
 * All rights reserved.
 */
class RestaurantChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@RestaurantChallengeApplication)
            modules(listOf(netWorkModule, dataBaseModule))
        }
    }
}