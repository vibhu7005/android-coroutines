package com.jordiee.coroutines.demonstrations.structuredconcurrency.kotlin

import kotlinx.coroutines.*
import java.math.BigInteger

 class FibonacciUseCaseUiCoroutines() {

    suspend fun computeFibonacci(index: Int): BigInteger = withContext(Dispatchers.Default) {
        if (index == 0) {
            BigInteger("0")
        } else if (index == 1) {
            BigInteger("1")
        } else {
            computeFibonacci(index - 1).add(computeFibonacci(index - 2))
        }
    }

    suspend fun waitForJordiee() {
        withContext(Dispatchers.Default) {
            delay(1000)
            println("hello jordiee")
        }
    }

}