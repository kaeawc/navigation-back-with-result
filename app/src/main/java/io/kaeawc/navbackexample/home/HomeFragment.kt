package io.kaeawc.navbackexample.home

import android.os.Bundle
import android.view.View
import io.kaeawc.navbackexample.BaseFragment
import io.kaeawc.navbackexample.R
import io.kaeawc.navbackexample.koinScope
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.scope.viewModel
import timber.log.Timber

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by koinScope.viewModel(this)

    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button?.setOnClickListener {
            viewModel.onButtonTapped()
        }
    }

    override fun onBackStackResult(result: Bundle) {
        Timber.i("result $result")
    }

}
