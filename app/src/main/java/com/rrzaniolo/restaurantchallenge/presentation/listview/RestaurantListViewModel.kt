package com.rrzaniolo.restaurantchallenge.presentation.listview

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.EmptyResultSetException
import com.rrzaniolo.movieapichallenge.presentation.base.BaseViewModel
import com.rrzaniolo.restaurantchallenge.data.models.RestaurantListResponse
import com.rrzaniolo.restaurantchallenge.data.models.RestaurantResponse
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import com.rrzaniolo.restaurantchallenge.domain.usecases.RestaurantListUseCase

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
        disposableTask.add(
            useCase.getRestaurants().subscribe(
                {
                    currentRestaurantList.addAll(parseListResponse(it))
                    _state.value = RestaurantListViewState.ShowError
                    _state.postValue(RestaurantListViewState.showList(currentRestaurantList))
                },
                {
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
            response.name, response.status, false, response.sortingValues.bestMatch,
            response.sortingValues.newest, response.sortingValues.ratingAverage,
            response.sortingValues.distance, response.sortingValues.popularity,
            response.sortingValues.averageProductPrice, response.sortingValues.deliveryCosts,
            response.sortingValues.minCost
        )
    }

    fun saveOrRemoveRestaurant(entity: RestaurantEntity){
        _state.value = RestaurantListViewState.ShowLoading
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
        disposableTask.add(useCase.saveRestaurant(entity).subscribe({
            _state.postValue(RestaurantListViewState.SaveSuccess)
        }, {
            entity.isFavorite = false
            _state.postValue(RestaurantListViewState.SaveError)
        }))
    }

    private fun deleteRestaurant(entity: RestaurantEntity) {
        entity.isFavorite = false
        disposableTask.addAll(useCase.deleteRestaurant(entity).subscribe({
            _state.postValue(RestaurantListViewState.DeleteSuccess)
        }, {
            entity.isFavorite = true
            _state.postValue(RestaurantListViewState.DeleteError)
        }))
    }

//    private fun sortList(){
//        currentRestaurantList.sortWith(
//            Comparator<RestaurantEntity> { t1, t2 ->
//                when {
//                    t1.isFavorite == t2.isFavorite -> {
//                        when{
//                            t1.status == t2.status -> {
//                                when{
//
//                                }
//                            }
//                            t1.status == "open" -> 1
//                            t2.status == "open" -> -1
//                            t1.status == "closed" -> -1
//                            t2.status == "closed" -> 1
//                            else -> 0
//                        }
//                    }
//                    t1.isFavorite -> 1
//                    t2.isFavorite -> -1
//                    else -> 0
//                }
//            }
//        )
//    }
}