package com.rrzaniolo.restaurantchallenge.di.modules

import com.rrzaniolo.restaurantchallenge.di.configurations.provideRestaurantApi
import org.koin.dsl.module

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */

val netWorkModule = module {
    single { provideRestaurantApi() }
}