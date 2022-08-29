package com.example.socialdolphin.features.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.socialdolphin.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

/**
 * An abstract Fragment to share some commons functions between its children
 *
 */
abstract class BaseFragment<VB: ViewBinding> : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = requireNotNull(_binding)

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding?.root?.allViews?.forEach {
            if (it is RecyclerView) {
                it.adapter = null
            }
        }
        _binding = null
    }

    abstract fun setupFragment()


    /**
     * Extensions
     */

    protected fun <T> LiveData<T>.observe(observer: Observer<T>) {
        if (view != null) this.observe(viewLifecycleOwner, observer)
    }

    // Navigation
    fun Fragment.navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    // Snackbar
    class SnackCallback(private val onDismiss: () -> Unit) : Snackbar.Callback() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            if (event == DISMISS_EVENT_TIMEOUT)
                onDismiss.invoke()
        }
    }

    enum class SnackBarType(val color: Int) {
        Success(R.color.green_success),
        Error(R.color.red_error),
        Warning(R.color.yellow_attention)
    }

    fun Fragment.showSnack(
        @StringRes message: Int?,
        type: SnackBarType,
        bottomNavigation: BottomNavigationView? = null,
        duration: Int = Snackbar.LENGTH_SHORT,
        onDismiss: (() -> Unit)? = null
    ) {
        if (message == null) return
        Snackbar.make(requireView(), message, duration)
            .setBackgroundTint(resources.getColor(type.color, null))
            .apply {
                if (bottomNavigation != null) this.anchorView = bottomNavigation
                if (onDismiss != null) addCallback(SnackCallback(onDismiss))
            }
            .show()
    }

}