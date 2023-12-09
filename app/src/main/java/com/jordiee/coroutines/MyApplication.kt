package com.jordiee.coroutines

import android.app.Application
import com.jordiee.coroutines.common.dependencyinjection.ApplicationCompositionRoot

class MyApplication : Application() {

    val applicationCompositionRoot =
        com.jordiee.coroutines.common.dependencyinjection.ApplicationCompositionRoot()

    override fun onCreate() {
        super.onCreate()
    }
}