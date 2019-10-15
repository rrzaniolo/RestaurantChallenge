package com.rrzaniolo.restaurantchallenge.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rrzaniolo.movieapichallenge.presentation.base.BaseRecyclerAdapter

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
@Entity
data class RestaurantEntity(
    @PrimaryKey val name: String,
    val status: Int,
    var isFavorite: Boolean = false,
    val bestMatch: Float,
    val newest: Float,
    val ratingAverage: Float,
    val distance: Int,
    val popularity: Float,
    val averageProductPrice: Int,
    val deliveryCosts: Int,
    val minCost: Int
): BaseRecyclerAdapter.BaseRecyclerItem(){

    override fun equals(other: Any?): Boolean {
        return other?.let {
            if(other is RestaurantEntity){
                other.name == this.name
            }else false
        }?:run { false }
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }


}

enum class RestaurantStatus(val statusCode: Int, val statusName: String){
    OPEN(0, "open"),
    ORDER_AHEAD(1, "order ahead"),
    CLOSED(2, "closed");

    companion object {
        fun statusCodeFromStatusName(statusName: String): Int = values().first { it.statusName == statusName }.statusCode
        fun statusNameFromStatusCode(statusCode: Int):String = values().first { it.statusCode == statusCode }.statusName
    }
}

enum class RestaurantSortingOptions{
    NEWEST, BEST_MATCH, DEFAULT, RATING_AVERAGE,
    DISTANCE, POPULARITY, AVERAGE_PRODUCT_PRICE,
    DELIVERY_COST, MIN_COST
}