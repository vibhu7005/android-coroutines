package com.jordiee.coroutines.demonstrations.structuredconcurrency.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FibonacciUseCase {
    interface Callback {
        fun onResultObtained(res : Int)
    }

    fun calcFibonacci(num: Int, callback: Callback ) {
        CoroutineScope(Dispatchers.Main).launch {
             callback.onResultObtained(calc(num))
        }
    }

    private fun calc(num: Int): Int {
        if (num == 0) {
            return 0
        } else if (num == 1) {
            return 1
        } else {
            return calc(num - 1) + calc(num - 2)
        }
    }
}