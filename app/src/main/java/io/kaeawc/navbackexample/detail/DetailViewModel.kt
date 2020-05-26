package io.kaeawc.navbackexample.detail

import androidx.lifecycle.ViewModel
import io.kaeawc.navbackexample.Navigator

class DetailViewModel(
    open val navigator: Navigator
) : ViewModel() {

    fun onBack() {
        navigator.back()
    }
}
