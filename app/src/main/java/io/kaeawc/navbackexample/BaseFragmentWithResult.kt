package io.kaeawc.navbackexample

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import timber.log.Timber

abstract class BaseFragmentWithResult : BaseFragment() {

    var result: Bundle? = null

    override fun onResume() {
        super.onResume()
        findNavController().previousBackStackEntry?.savedStateHandle?.remove<Bundle>("result")
    }

    override suspend fun onBackPressed(): Boolean {
        Timber.i("onBackPressed")
        saveResult(result)
        result = null
        return false
    }

    override fun onPause() {
        super.onPause()
    }

    open fun saveResult(result: Bundle?) {
        findNavController().previousBackStackEntry?.savedStateHandle?.apply {
            when (result) {
                null -> remove("result")
                else -> set("result", result)
            }
        }
    }
}
