package com.jordiee.coroutines.demonstrations.structuredconcurrency.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class FibonacciUseCaseTest {
    lateinit var fibonacciUseCase: FibonacciUseCase
    lateinit var callback: FibonacciUseCase.Callback
    var result : Int? = null
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        fibonacciUseCase = FibonacciUseCase(testCoroutineDispatcher)
        callback = object : FibonacciUseCase.Callback {
            override fun onResultObtained(res: Int) {
                result = res
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testcase1() {
        testCoroutineDispatcher.runBlockingTest {
            fibonacciUseCase.calcFibonacci(3, callback)
            assert(result == 2)
        }
    }
}