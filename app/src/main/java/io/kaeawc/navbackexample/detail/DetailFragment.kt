package io.kaeawc.navbackexample.detail

import android.os.Bundle
import io.kaeawc.navbackexample.BaseFragmentWithResult
import io.kaeawc.navbackexample.R
import io.kaeawc.navbackexample.koinScope
import org.koin.androidx.viewmodel.scope.viewModel
import timber.log.Timber

class DetailFragment : BaseFragmentWithResult() {

    private val viewModel: DetailViewModel by koinScope.viewModel(this)

    override val layoutRes: Int
        get() = R.layout.fragment_detail

    override fun onResume() {
        super.onResume()

        Timber.i("result = Bundle().apply { putString(\"key\", \"result\") }")
        result = Bundle().apply { putString("key", "result") }
    }
}
