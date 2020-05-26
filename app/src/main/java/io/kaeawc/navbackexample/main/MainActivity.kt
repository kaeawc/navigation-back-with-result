package io.kaeawc.navbackexample.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.fragment.app.findFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import io.kaeawc.navbackexample.BaseFragment
import io.kaeawc.navbackexample.R
import io.kaeawc.navbackexample.Route
import io.kaeawc.navbackexample.koinScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.scope.viewModel
import timber.log.Timber

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by koinScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            viewModel.getLiveRouting()
                .flowOn(Dispatchers.IO)
                .onEach(::onNavigate)
                .launchIn(this)
        }
    }

    private suspend fun onNavigate(route: Route) {
        Timber.i("onNavigate $route")
        lifecycleScope.launch {
            when (route) {
                is Route.Forward -> navigateTo(route.direction)
                Route.Back -> navigateBack()
            }
        }
    }

    /**
     * Navigates to the specified destination using
     * Navigation Component or starting an activity.
     */
    private fun navigateTo(route : NavDirections) {
        if (isFinishing) return

        try {
            Navigation.findNavController(this, R.id.nav_host).apply {
                navigate(route)
            }
        } catch (ex: Exception) {
            Timber.e(ex, "oops")
            /**
             * Could not perform navigation, possibly the current fragment
             * does not have a route to the destination
             */
        }
    }

    private fun navigateBack() {
        Timber.i("navigateBack")
        Navigation.findNavController(this, R.id.nav_host).apply {
            super.onBackPressed()
        }
    }

    private fun getCurrentFragment(): BaseFragment? {
        return try {
            nav_host?.childFragmentManager?.fragments?.firstOrNull() as? BaseFragment
        } catch (ex: Throwable) {
            null
        }
    }

    override fun onBackPressed() {
        Timber.d("onBackPressed intent uri ${intent?.data}")

        val currentFragment = getCurrentFragment()
        Timber.i("currentFragment $currentFragment")

        Navigation.findNavController(this, R.id.nav_host).apply {

            lifecycleScope.launch {
                val processingBack = currentFragment?.onBackPressed() ?: false
                Timber.i("processingBack $processingBack")
                if (!processingBack) {
                    super.onBackPressed()
                }
            }
        }
    }

}
