package com.rrzaniolo.restaurantchallenge.presentation

import androidx.lifecycle.MutableLiveData
import androidx.room.EmptyResultSetException
import com.rrzaniolo.restaurantchallenge.data.models.RestaurantListResponse
import com.rrzaniolo.restaurantchallenge.data.models.RestaurantResponse
import com.rrzaniolo.restaurantchallenge.data.models.RestaurantSortingValuesResponse
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import com.rrzaniolo.restaurantchallenge.domain.usecases.RestaurantListUseCase
import com.rrzaniolo.restaurantchallenge.presentation.listview.RestaurantListViewModel
import com.rrzaniolo.restaurantchallenge.presentation.listview.RestaurantListViewState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/16/2019.
 * All rights reserved.
 */
class RestaurantListViewModelTestSuit: AutoCloseKoinTest() {
    lateinit var disposableTask: CompositeDisposable

    lateinit var useCase: RestaurantListUseCase
    lateinit var viewModel: RestaurantListViewModel

    @Before
    fun setUp(){
        disposableTask = mock(CompositeDisposable::class.java)
        useCase = mock(RestaurantListUseCase::class.java)

        viewModel = RestaurantListViewModel(useCase)
    }

    @Test
    fun `Assert onCleared`(){
        viewModel.disposableTask = disposableTask
        viewModel.onCleared()

        verify(viewModel.disposableTask, times(1)).clear()
    }

    @Test
    fun`Assert getRestaurants success`(){
        val mockedResponse = RestaurantResponse(
            "", "open",
            RestaurantSortingValuesResponse(
                0.0f, 0.0f, 0.0f, 0,
                0.0f, 0, 0,0)
        )
        val mockedListResponse = mock(RestaurantListResponse::class.java)
        `when`(mockedListResponse.restaurantList).thenReturn(listOf(mockedResponse))

        viewModel.disposableTask = disposableTask
        viewModel._state = mock(MutableLiveData<RestaurantListViewState>()::class.java)

        `when`(useCase.getRestaurants())
            .thenReturn(Flowable.just(mockedListResponse))
        `when`(useCase.getFavoriteRestaurants())
            .thenReturn(Flowable.just(arrayListOf(mock(RestaurantEntity::class.java))))

        `when`(viewModel._state.postValue(ArgumentMatchers.any()))
            .then{ assert(
                it.arguments[0] is RestaurantListViewState.ShowLoading ||
                        it.arguments[0] is RestaurantListViewState.showList
            ) }

        viewModel.getRestaurants()
        verify(viewModel.disposableTask, times(2)).clear()

    }

    @Test
    fun`Assert getRestaurants error on useCase-getRestaurants`(){
        viewModel.disposableTask = disposableTask
        viewModel._state = mock(MutableLiveData<RestaurantListViewState>()::class.java)

        `when`(useCase.getRestaurants())
            .thenReturn(Flowable.error((Throwable())))
        `when`(useCase.getFavoriteRestaurants())
            .thenReturn(Flowable.just(arrayListOf(mock(RestaurantEntity::class.java))))

        `when`(viewModel._state.postValue(ArgumentMatchers.any()))
            .then{ assert(
                it.arguments[0] is RestaurantListViewState.ShowLoading ||
                        it.arguments[0] is RestaurantListViewState.showList
            ) }

        viewModel.getRestaurants()
        verify(viewModel.disposableTask, times(2)).clear()

    }

    @Test
    fun`Assert getRestaurants error on useCase-getFavoriteRestaurants`(){
        viewModel.disposableTask = disposableTask
        viewModel._state = mock(MutableLiveData<RestaurantListViewState>()::class.java)

        `when`(useCase.getRestaurants())
            .thenReturn(Flowable.just(mock(RestaurantListResponse::class.java)))
        `when`(useCase.getFavoriteRestaurants())
            .thenReturn(Flowable.error((Throwable())))

        `when`(viewModel._state.postValue(ArgumentMatchers.any()))
            .then{ assert(
                it.arguments[0] is RestaurantListViewState.ShowLoading ||
                        it.arguments[0] is RestaurantListViewState.showList
            ) }

        viewModel.getRestaurants()
        verify(viewModel.disposableTask, times(2)).clear()

    }

    @Test
    fun`Assert getRestaurants error`(){
        viewModel.disposableTask = disposableTask
        viewModel._state = mock(MutableLiveData<RestaurantListViewState>()::class.java)

        `when`(useCase.getRestaurants())
            .thenReturn(Flowable.error((Throwable())))
        `when`(useCase.getFavoriteRestaurants())
            .thenReturn(Flowable.error((Throwable())))

        `when`(viewModel._state.postValue(ArgumentMatchers.any()))
            .then{ assert(
                it.arguments[0] is RestaurantListViewState.ShowLoading ||
                        it.arguments[0] is RestaurantListViewState.ShowError
            ) }

        viewModel.getRestaurants()
        verify(viewModel.disposableTask, times(2)).clear()

    }

    @Test
    fun`Assert saveOrRemoveRestaurant save success`(){
        viewModel.disposableTask = disposableTask

        val mockedEntity = mock(RestaurantEntity::class.java)
        `when`(mockedEntity.name).thenReturn("")

        viewModel._state = mock(MutableLiveData<RestaurantListViewState>()::class.java)

        `when`(useCase.getRestaurantByName(ArgumentMatchers.anyString()))
            .thenReturn(Single.error(EmptyResultSetException("")))
        `when`(useCase.saveRestaurant(mockedEntity))
            .thenReturn(Completable.complete())

        `when`(viewModel._state.postValue(ArgumentMatchers.any()))
            .then{ assert(
                it.arguments[0] is RestaurantListViewState.ShowLoading ||
                        it.arguments[0] is RestaurantListViewState.SaveSuccess
            ) }

        viewModel.saveOrRemoveRestaurant(mockedEntity)
        verify(viewModel.disposableTask, times(2)).clear()
    }

    @Test
    fun`Assert saveOrRemoveRestaurant save error`(){
        viewModel.disposableTask = disposableTask

        val mockedEntity = mock(RestaurantEntity::class.java)
        `when`(mockedEntity.name).thenReturn("")

        viewModel._state = mock(MutableLiveData<RestaurantListViewState>()::class.java)

        `when`(useCase.getRestaurantByName(ArgumentMatchers.anyString()))
            .thenReturn(Single.error(EmptyResultSetException("")))
        `when`(useCase.saveRestaurant(mockedEntity))
            .thenReturn(Completable.error(Throwable()))

        `when`(viewModel._state.postValue(ArgumentMatchers.any()))
            .then{ assert(
                it.arguments[0] is RestaurantListViewState.ShowLoading ||
                        it.arguments[0] is RestaurantListViewState.SaveError
            ) }

        viewModel.saveOrRemoveRestaurant(mockedEntity)
        verify(viewModel.disposableTask, times(2)).clear()
    }

    @Test
    fun`Assert saveOrRemoveRestaurant delete success`(){
        viewModel.disposableTask = disposableTask

        val mockedEntity = mock(RestaurantEntity::class.java)
        `when`(mockedEntity.name).thenReturn("")

        viewModel._state = mock(MutableLiveData<RestaurantListViewState>()::class.java)

        `when`(useCase.getRestaurantByName(ArgumentMatchers.anyString()))
            .thenReturn(Single.just(mockedEntity))
        `when`(useCase.deleteRestaurant(mockedEntity))
            .thenReturn(Completable.complete())

        `when`(viewModel._state.postValue(ArgumentMatchers.any()))
            .then{ assert(
                it.arguments[0] is RestaurantListViewState.ShowLoading ||
                        it.arguments[0] is RestaurantListViewState.DeleteSuccess
            ) }

        viewModel.saveOrRemoveRestaurant(mockedEntity)
        verify(viewModel.disposableTask, times(2)).clear()
    }

    @Test
    fun`Assert saveOrRemoveRestaurant delete error`(){
        viewModel.disposableTask = disposableTask

        val mockedEntity = mock(RestaurantEntity::class.java)
        `when`(mockedEntity.name).thenReturn("")

        viewModel._state = mock(MutableLiveData<RestaurantListViewState>()::class.java)

        `when`(useCase.getRestaurantByName(ArgumentMatchers.anyString()))
            .thenReturn(Single.just(mockedEntity))
        `when`(useCase.deleteRestaurant(mockedEntity))
            .thenReturn(Completable.error(Throwable()))

        `when`(viewModel._state.postValue(ArgumentMatchers.any()))
            .then{ assert(
                it.arguments[0] is RestaurantListViewState.ShowLoading ||
                        it.arguments[0] is RestaurantListViewState.DeleteError
            ) }

        viewModel.saveOrRemoveRestaurant(mockedEntity)
        verify(viewModel.disposableTask, times(2)).clear()

    }

    @Test
    fun`Assert saveOrRemoveRestaurant error`(){
        viewModel.disposableTask = disposableTask

        val mockedEntity = mock(RestaurantEntity::class.java)
        `when`(mockedEntity.name).thenReturn("")

        viewModel._state = mock(MutableLiveData<RestaurantListViewState>()::class.java)

        `when`(useCase.getRestaurantByName(ArgumentMatchers.anyString()))
            .thenReturn(Single.error(Throwable()))

        `when`(viewModel._state.postValue(ArgumentMatchers.any()))
            .then{ assert(
                it.arguments[0] is RestaurantListViewState.ShowLoading ||
                        it.arguments[0] is RestaurantListViewState.ShowError
            ) }

        viewModel.saveOrRemoveRestaurant(mockedEntity)
        verify(viewModel.disposableTask, times(1)).clear()
    }
}