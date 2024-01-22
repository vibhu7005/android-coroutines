package com.jordiee.coroutines.demonstrations.structuredconcurrency.kotlin

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

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

    @Test
    fun testCoroutineExceptionhandling() {
        runBlocking {
            val scopeJob = Job()
            val exceptionhandling = CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
                println("exception handled $throwable")
            }
            val scope = CoroutineScope(scopeJob + exceptionhandling)
            val scope1 = scope.launch() {
                delay(100)
                println("scope 1 completed")
            }
            val scope2 = scope.launch {
                delay(50)
                throw RuntimeException()
            }
            joinAll(scope1, scope2)
            println(scope)
            println(scope1)
            println(scope2)
        }
    }
}