package com.jordiee.coroutines.exercises.exercise1

import com.jordiee.coroutines.common.ThreadInfoLogger
import kotlin.random.Random

class GetReputationEndpoint {
    fun getReputation(userId: String): Int {
        com.jordiee.coroutines.common.ThreadInfoLogger.logThreadInfo("GetReputationEndpoint#getReputation(): called")
        Thread.sleep(3000)
        com.jordiee.coroutines.common.ThreadInfoLogger.logThreadInfo("GetReputationEndpoint#getReputation(): return data")
        return Random.nextInt(0, 100)
    }
}