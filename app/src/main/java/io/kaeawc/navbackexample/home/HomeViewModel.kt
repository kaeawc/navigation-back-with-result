package io.kaeawc.navbackexample.home

import androidx.lifecycle.ViewModel
import io.kaeawc.navbackexample.Navigator

class HomeViewModel(
    open val navigator: Navigator
) : ViewModel() {

    fun onButtonTapped() {
        navigator.goTo(HomeFragmentDirections.actionHomeFragmentToDetailFragment())
    }

    fun onBack() {
        navigator.back()
    }
}
