package com.jordiee.coroutines.exercises.exercise6

import com.jordiee.coroutines.common.ThreadInfoLogger
import com.jordiee.coroutines.common.ThreadInfoLogger.logThreadInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Existing class which you can't change
 */
class PostBenchmarkResultsEndpoint {
    fun postBenchmarkResults(timeSeconds: Int, iterations: Long) {
        Thread.sleep(500)
    }
}