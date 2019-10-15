package com.rrzaniolo.restaurantchallenge.presentation.listview

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.EmptyResultSetException
import com.rrzaniolo.restaurantchallenge.data.models.RestaurantListResponse
import com.rrzaniolo.restaurantchallenge.data.models.RestaurantResponse
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantSortingOptions
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantStatus
import com.rrzaniolo.restaurantchallenge.domain.usecases.RestaurantListUseCase
import com.rrzaniolo.restaurantchallenge.presentation.base.BaseViewModel

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
class RestaurantListViewModel(private val useCase: RestaurantListUseCase): BaseViewModel() {

    @Suppress("PropertyName")
    @VisibleForTesting var _state = MutableLiveData<RestaurantListViewState>()
    val state: LiveData<RestaurantListViewState>
    get() = _state

    private val currentRestaurantList = ArrayList<RestaurantEntity>()

    fun getRestaurants() {
        _state.value = RestaurantListViewState.ShowLoading
        currentRestaurantList.clear()
        disposableTask.clear()
        disposableTask.add(
            useCase.getFavoriteRestaurants().subscribe(
                {
                    currentRestaurantList.addAll(it)
                    getRestaurantsFromServer()
                },
                {
                    getRestaurantsFromServer()
                }
            )
        )
    }
    private fun getRestaurantsFromServer(){
        disposableTask.clear()
        disposableTask.add(
            useCase.getRestaurants().subscribe(
                {
                    parseListResponse(it).forEach {entity ->
                        if(!currentRestaurantList.contains(entity)) currentRestaurantList.add(entity)
                    }
                    sortList()
                    _state.postValue(RestaurantListViewState.showList(currentRestaurantList))
                },
                {
                    sortList()
                    if(currentRestaurantList.isEmpty()) _state.value = RestaurantListViewState.ShowError
                    else _state.postValue(RestaurantListViewState.showList(currentRestaurantList))
                }
            )
        )
    }
    private fun parseListResponse(response: RestaurantListResponse): ArrayList<RestaurantEntity>{
        val list = ArrayList<RestaurantEntity> ()
        response.restaurantList.forEach { restaurant ->
            list.add(parseResponse(restaurant))
        }

        return list
    }
    private fun parseResponse(response: RestaurantResponse): RestaurantEntity{
        return RestaurantEntity(
            response.name, RestaurantStatus.statusCodeFromStatusName(response.status), false,
            response.sortingValues.bestMatch, response.sortingValues.newest, response.sortingValues.ratingAverage,
            response.sortingValues.distance, response.sortingValues.popularity, response.sortingValues.averageProductPrice,
            response.sortingValues.deliveryCosts, response.sortingValues.minCost
        )
    }

    fun saveOrRemoveRestaurant(entity: RestaurantEntity){
        _state.value = RestaurantListViewState.ShowLoading
        disposableTask.clear()
        disposableTask.add(
            useCase.getRestaurantByName(entity.name).subscribe(
                {
                    deleteRestaurant(it)
                },
                {
                    if(it is EmptyResultSetException) saveRestaurant(entity)
                    else _state.postValue(RestaurantListViewState.ShowError)
                }
            )
        )
    }
    private fun saveRestaurant(entity: RestaurantEntity) {
        entity.isFavorite = true
        disposableTask.clear()
        disposableTask.add(useCase.saveRestaurant(entity).subscribe({
            _state.postValue(RestaurantListViewState.SaveSuccess)
        }, {
            entity.isFavorite = false
            _state.postValue(RestaurantListViewState.SaveError)
        }))
    }

    private fun deleteRestaurant(entity: RestaurantEntity) {
        entity.isFavorite = false
        disposableTask.clear()
        disposableTask.add(useCase.deleteRestaurant(entity).subscribe({
            _state.postValue(RestaurantListViewState.DeleteSuccess)
        }, {
            entity.isFavorite = true
            _state.postValue(RestaurantListViewState.DeleteError)
        }))
    }

    private fun sortList(sort: RestaurantSortingOptions? = null){
        val comparator = when(sort) {
            RestaurantSortingOptions.AVERAGE_PRODUCT_PRICE -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::averageProductPrice)
            RestaurantSortingOptions.BEST_MATCH -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::bestMatch)
            RestaurantSortingOptions.DELIVERY_COST -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::deliveryCosts)
            RestaurantSortingOptions.DISTANCE -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::distance)
            RestaurantSortingOptions.MIN_COST -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::minCost)
            RestaurantSortingOptions.NEWEST -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::newest)
            RestaurantSortingOptions.POPULARITY -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::popularity)
            RestaurantSortingOptions.RATING_AVERAGE -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::ratingAverage)
            else -> compareBy({!it.isFavorite}, RestaurantEntity::status)
        }
        currentRestaurantList.sortWith(comparator)
    }
}