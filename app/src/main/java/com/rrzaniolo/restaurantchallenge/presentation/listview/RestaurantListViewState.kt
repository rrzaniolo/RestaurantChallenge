package com.rrzaniolo.restaurantchallenge.presentation.listview

import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
open class RestaurantListViewState {

    data class showList(val restaurants: ArrayList<RestaurantEntity>): RestaurantListViewState()

    object ShowLoading: RestaurantListViewState()
    object ShowError: RestaurantListViewState()
    object SaveSuccess: RestaurantListViewState()
    object SaveError: RestaurantListViewState()
    object DeleteSuccess: RestaurantListViewState()
    object DeleteError: RestaurantListViewState()
}