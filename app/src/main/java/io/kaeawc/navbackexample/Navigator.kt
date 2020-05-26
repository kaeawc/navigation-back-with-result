package io.kaeawc.navbackexample

import androidx.navigation.NavDirections
import kotlinx.coroutines.channels.Channel
import timber.log.Timber

open class Navigator {

    open val directions: Channel<Route> = Channel(1)

    open fun goTo(destination: NavDirections) {
        directions.offer(Route.Forward(destination))
    }

    open fun back() {
        directions.offer(Route.Back)
    }
}
