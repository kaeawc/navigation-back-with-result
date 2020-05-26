package io.kaeawc.navbackexample

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import io.kaeawc.navbackexample.detail.DetailFragment
import io.kaeawc.navbackexample.detail.DetailViewModel
import io.kaeawc.navbackexample.home.HomeFragment
import io.kaeawc.navbackexample.home.HomeViewModel
import io.kaeawc.navbackexample.main.MainActivity
import io.kaeawc.navbackexample.main.MainViewModel
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.scope.*
import org.koin.androidx.viewmodel.ViewModelParameter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.context.startKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module


fun App.initKoin() {
    startKoin {
        androidContext(this@initKoin)
        modules(
            listOf(
                appModule,
                scopedModules
            )
        )
    }
}

private val appModule = module {
    single { Navigator() }
    single { Acorn(logcatEnabled = true) }
}


private val scopedModules = module {

    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
    }

    scope(named<HomeFragment>()) {
        viewModel { HomeViewModel(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { DetailViewModel(get()) }
    }
}

/**
 * We added this because Koin doesn't yet have this extension function in order to handle
 * Navigation Component's concept of navigation graph scoped ViewModels.
 *
 * TODO: Once this issue is resolved lets delete this function and use the standard one
 *     https://github.com/InsertKoinIO/koin/issues/442
 */
inline fun <reified VM : ViewModel> Fragment.sharedGraphViewModel(
    @IdRes navGraphId: Int,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = lazy {
    val store = NavHostFragment.findNavController(this).getViewModelStoreOwner(navGraphId).viewModelStore
    getKoin().getViewModel(ViewModelParameter(VM::class, qualifier, parameters, null, store, null))
}

val LifecycleOwner.koinScope: Scope
    get() = this.lifecycleScope
