package com.jordiee.coroutines.demonstrations.structuredconcurrency.kotlin

import androidx.lifecycle.viewmodel.CreationExtras
import com.jordiee.coroutines.common.TestUtils.printCoroutineScopeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigInteger
import kotlin.coroutines.EmptyCoroutineContext

@ExperimentalCoroutinesApi
class FibonacciUseCaseUiCoroutinesTest {

    private lateinit var SUT: FibonacciUseCaseUiCoroutines

    @Before
    fun setup() {
        SUT = FibonacciUseCaseUiCoroutines()
    }

    @Test
    fun computeFibonacci_0_returns0() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFibonacci(0)
            // Assert
            assertThat(result, `is`(BigInteger("0")))
        }
    }

    @Test
    fun computeFibonacci_1_returns1() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFibonacci(1)
            // Assert
            assertThat(result, `is`(BigInteger("1")))
        }
    }


    @Test
    fun computeFibonacci_10_returnsCorrectResult() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFibonacci(10)
            // Assert
            assertThat(result, `is`(BigInteger("55")))
        }
    }

    @Test
    fun computeFibonacci_30_returnsCorrectResult() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFibonacci(30)
            // Assert
            assertThat(result, `is`(BigInteger("832040")))
        }
    }

    @Test
    fun BackgroundOperationTestCase() {
        runBlocking {
            val jobScope = Job()
            val job = CoroutineScope(jobScope).launch {
                delay(100)
                println("jordiee your coroutine is executed")
            }
            job.join()
            println("test completed")
        }
    }

}