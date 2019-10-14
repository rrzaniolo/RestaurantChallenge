package com.rrzaniolo.restaurantchallenge.di.modules

import com.rrzaniolo.restaurantchallenge.data.repositories.RestaurantRepositoryImp
import com.rrzaniolo.restaurantchallenge.domain.repositories.RestaurantRepository
import com.rrzaniolo.restaurantchallenge.domain.usecases.RestaurantListUseCase
import com.rrzaniolo.restaurantchallenge.presentation.listview.RestaurantListViewModel
import com.squareup.moshi.Moshi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/14/2019.
 * All rights reserved.
 */
internal val listViewModule = module {
    factory<RestaurantRepository> { RestaurantRepositoryImp(Moshi.Builder().build(), get()) }
    factory { RestaurantListUseCase(get()) }
    viewModel { RestaurantListViewModel(get()) }
}
internal val provideListViewModule = loadKoinModules(listViewModule)
fun loadListViewModule() = provideListViewModule