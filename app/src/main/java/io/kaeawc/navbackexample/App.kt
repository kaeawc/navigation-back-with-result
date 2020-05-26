package io.kaeawc.navbackexample

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class App : Application() {

    private val acorn: Acorn by inject { parametersOf(this) }

    override fun onCreate() {
        super.onCreate()
        initKoin()
        Timber.plant(acorn)
    }
}
