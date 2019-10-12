package com.rrzaniolo.restaurantchallenge.domain.entities

import androidx.room.Entity
import com.rrzaniolo.movieapichallenge.presentation.base.BaseRecyclerAdapter

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
@Entity
data class RestaurantEntity(
    val name: String,
    val status: String,
    var isFavorite: Boolean,
    val bestMatch: Float,
    val newest: Float,
    val ratingAverage: Float,
    val distance: Int,
    val popularity: Float,
    val averageProductPrice: Int,
    val deliveryCosts: Int,
    val minCost: Int
): BaseRecyclerAdapter.BaseRecyclerItem()