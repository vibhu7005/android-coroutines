package com.jordiee.coroutines.common

import android.app.Fragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jordiee.coroutines.MainActivity

abstract class BaseFragment : Fragment() {

    protected open val screenTitle = ""

    protected val compositionRoot get() = (requireActivity() as com.jordiee.coroutines.MainActivity).compositionRoot

    protected lateinit var screensNavigator: com.jordiee.coroutines.common.ScreensNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screensNavigator = compositionRoot.screensNavigator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbarManipulator = compositionRoot.toolbarManipulator
        toolbarManipulator.setScreenTitle(screenTitle)
        if (screensNavigator.isRootScreen()) {
            toolbarManipulator.hideUpButton()
        } else {
            toolbarManipulator.showUpButton()
        }
    }


}