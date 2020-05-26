package io.kaeawc.navbackexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import timber.log.Timber

abstract class BaseFragment : Fragment() {

    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBackStackResult()?.let { result ->
            onBackStackResult(result)
            clearBackStackResult()
        }
    }

    open suspend fun onBackPressed(): Boolean {
        return false
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        backCallback.remove()
//    }

    open fun getBackStackResult(): Bundle? {
        return findNavController().currentBackStackEntry?.savedStateHandle?.get<Bundle>("result")
    }

    open fun clearBackStackResult() {
        Timber.i("clearBackStackResult")
        findNavController().currentBackStackEntry?.savedStateHandle?.apply {
//            set("result", null)
            remove<Bundle>("result")
        }
    }

    open fun onBackStackResult(result: Bundle) {}
}
