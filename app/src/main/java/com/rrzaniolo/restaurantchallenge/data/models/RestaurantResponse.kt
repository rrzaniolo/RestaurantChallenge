package com.rrzaniolo.restaurantchallenge.data.models

import androidx.room.Entity
import com.squareup.moshi.Json

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
data class RestaurantListResponse(
   @field:Json(name = "restaurants") val restaurantList: ArrayList<RestaurantResponse>
)

@Entity
data class RestaurantResponse(
   @field:Json(name = "name") val name: String,
   @field:Json(name = "status") val status: String,
   @field:Json(name = "sortingValues") val sortingValues: RestaurantSortingValuesResponse

)

@Entity
data class RestaurantSortingValuesResponse(
   @field:Json(name = "bestMatch") val bestMatch: Float,
   @field:Json(name = "newest") val newest: Float,
   @field:Json(name = "ratingAverage") val ratingAverage: Float,
   @field:Json(name = "distance") val distance: Int,
   @field:Json(name = "popularity") val popularity: Float,
   @field:Json(name = "averageProductPrice") val averageProductPrice: Int,
   @field:Json(name = "deliveryCosts") val deliveryCosts: Int,
   @field:Json(name = "minCost") val minCost: Int
)