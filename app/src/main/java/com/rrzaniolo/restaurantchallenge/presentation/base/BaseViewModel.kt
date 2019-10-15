package com.rrzaniolo.restaurantchallenge.presentation.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
abstract class BaseViewModel: ViewModel() {

    @VisibleForTesting var disposableTask = CompositeDisposable()

    @VisibleForTesting
    public override fun onCleared() {
        super.onCleared()
        disposableTask.clear()
    }
}