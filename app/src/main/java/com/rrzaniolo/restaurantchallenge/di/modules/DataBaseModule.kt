package com.rrzaniolo.restaurantchallenge.di.modules

import com.rrzaniolo.restaurantchallenge.di.configurations.provideDataBaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
internal val dataBaseModule = module {
    single {
        provideDataBaseBuilder(
            androidContext()
        )
    }
}