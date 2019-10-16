package com.rrzaniolo.restaurantchallenge.domain

import com.rrzaniolo.restaurantchallenge.data.models.RestaurantListResponse
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import com.rrzaniolo.restaurantchallenge.domain.repositories.RestaurantRepository
import com.rrzaniolo.restaurantchallenge.domain.usecases.RestaurantListUseCase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableEmpty
import io.reactivex.internal.operators.completable.CompletableError
import io.reactivex.internal.operators.flowable.FlowableError
import io.reactivex.internal.operators.flowable.FlowableJust
import io.reactivex.internal.operators.single.SingleError
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito.*

/**
 * Created by Rodrigo Rodrigues Zaniolo on 9/30/2019.
 * All rights reserved.
 */
class RestaurantListUseCaseTestSuit: AutoCloseKoinTest() {

    lateinit var restaurantRepository: RestaurantRepository

    lateinit var useCase: RestaurantListUseCase

    @Before
    fun setUp(){
        restaurantRepository = mock(RestaurantRepository::class.java)

        useCase = RestaurantListUseCase(
            restaurantRepository
        )
    }

    @Test
    fun `Assert getFavoriteRestaurants success`(){
        `when`(restaurantRepository.getRestaurantsLocally())
            .thenReturn(Flowable
                .just(arrayListOf(mock(RestaurantEntity::class.java)))
            )

        val response = useCase.getFavoriteRestaurants()

        assert(response is FlowableJust)
    }

    @Test
    fun `Assert getFavoriteRestaurants error`(){
        `when`(restaurantRepository.getRestaurantsLocally())
            .thenReturn(Flowable
                .error(Throwable())
            )


        val response = useCase.getFavoriteRestaurants()

        assert(response is FlowableError)
    }

    @Test
    fun `Assert getRestaurantByName success`(){
        `when`(restaurantRepository.getRestaurantByName(anyString()))
            .thenReturn(Single
                .just(mock(RestaurantEntity::class.java))
            )

        val response = useCase.getRestaurantByName("")

        assert(response.blockingGet() is RestaurantEntity)
    }

    @Test
    fun `Assert getRestaurantByName error`(){
        `when`(restaurantRepository.getRestaurantByName(anyString()))
            .thenReturn(Single
                .error(Throwable())
            )


        val response = useCase.getRestaurantByName("")

        assert(response is SingleError)
    }

    @Test
    fun `Assert getRestaurants success`(){
        `when`(restaurantRepository.getRestaurants())
            .thenReturn(Flowable
                .just(mock(RestaurantListResponse::class.java))
            )

        val response = useCase.getRestaurants()

        assert(response.blockingFirst() is RestaurantListResponse)
    }

    @Test
    fun `Assert getRestaurants error`(){
        `when`(restaurantRepository.getRestaurants())
            .thenReturn(Flowable
                .error(Throwable())
            )


        val response = useCase.getRestaurants()

        assert(response is FlowableError)
    }

    @Test
    fun `Assert saveRestaurant success`(){
        val mockedEntity = mock(RestaurantEntity::class.java)
        `when`(restaurantRepository.saveRestaurant(mockedEntity))
            .thenReturn(
                Completable.complete()
            )

        val response = useCase.saveRestaurant(mockedEntity)

        assert(response is CompletableEmpty)
    }

    @Test
    fun `Assert saveRestaurant error`(){
        val mockedEntity = mock(RestaurantEntity::class.java)
        `when`(restaurantRepository.saveRestaurant(mockedEntity))
            .thenReturn(
                Completable.error(Throwable())
            )

        val response = useCase.saveRestaurant(mockedEntity)

        assert(response is CompletableError)
    }

    @Test
    fun `Assert deleteRestaurant success`(){
        val mockedEntity = mock(RestaurantEntity::class.java)
        `when`(restaurantRepository.deleteRestaurant(mockedEntity))
            .thenReturn(
                Completable.complete()
            )

        val response = useCase.deleteRestaurant(mockedEntity)

        assert(response is CompletableEmpty)
    }

    @Test
    fun `Assert deleteRestaurant error`(){
        val mockedEntity = mock(RestaurantEntity::class.java)
        `when`(restaurantRepository.deleteRestaurant(mockedEntity))
            .thenReturn(
                Completable.error(Throwable())
            )

        val response = useCase.deleteRestaurant(mockedEntity)

        assert(response is CompletableError)
    }
}