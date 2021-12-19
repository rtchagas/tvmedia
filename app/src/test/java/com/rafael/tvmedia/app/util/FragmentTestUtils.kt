package com.rafael.tvmedia.app.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.testing.TestNavHostController
import com.rafael.tvmedia.R

val FragmentManager.currentNavigationFragment: Fragment?
    get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()

val FragmentManager.currentNavigationId: Int?
    get() = primaryNavigationFragment?.let {
        NavHostFragment.findNavController(it)
    }?.currentDestination?.id

fun Fragment.setTestNavigation() {
    val navController = TestNavHostController(requireContext())
    navController.setGraph(R.navigation.nav_graph)
    view?.let { Navigation.setViewNavController(it, navController) }
}

fun Fragment.setTestDestination(@IdRes destination: Int) {
    (findNavController() as TestNavHostController)
        .setCurrentDestination(destination)
}

inline fun <reified T : Fragment> launchFragment(
    initialState: Lifecycle.State = Lifecycle.State.RESUMED,
    @IdRes destination: Int? = null
): FragmentScenario<T> {

    return launchFragmentWithArgs(initialState = initialState, destination = destination)
}

inline fun <reified T : Fragment> launchFragmentWithArgs(
    initialState: Lifecycle.State = Lifecycle.State.RESUMED,
    @IdRes destination: Int? = null,
    navArgs: Bundle? = null
): FragmentScenario<T> {

    val scenario = launchFragmentInContainer<T>(
        initialState = initialState,
        themeResId = R.style.AppTheme,
        fragmentArgs = navArgs
    )

    scenario.withFragment {
        setTestNavigation()
        destination?.let { setTestDestination(it) }
        this
    }

    return scenario
}
