package io.kaeawc.navbackexample.main

import androidx.lifecycle.ViewModel
import io.kaeawc.navbackexample.Navigator
import io.kaeawc.navbackexample.Route
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import timber.log.Timber

class MainViewModel(
    private val navigator: Navigator
) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun getLiveRouting(): Flow<Route> {
        Timber.i("return navigator.directions.consumeAsFlow()")
        return navigator.directions.consumeAsFlow()
    }
}
