package com.jordiee.coroutines.common

import android.util.Log

object ThreadInfoLogger {

    private const val TAG = "ThreadInfoLogger"

    fun logThreadInfo(message: String) {
        Log.i(com.jordiee.coroutines.common.ThreadInfoLogger.TAG, "$message; thread name: ${Thread.currentThread().name}; thread ID: ${Thread.currentThread().id}")
    }

}