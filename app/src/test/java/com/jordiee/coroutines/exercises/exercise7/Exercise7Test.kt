package com.jordiee.coroutines.exercises.exercise7

import com.jordiee.coroutines.common.TestUtils
import com.jordiee.coroutines.common.TestUtils.printCoroutineScopeInfo
import com.jordiee.coroutines.common.TestUtils.printJobsHierarchy
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.Exception
import kotlin.coroutines.EmptyCoroutineContext

class Exercise7Test {

    /*
    Write nested withContext blocks, explore the resulting Job's hierarchy, test cancellation
    of the outer scope
     */
    @Test
    fun nestedWithContext() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + CoroutineName("outer scope") + Dispatchers.IO)
            val mainJob = scope.launch {
                try {
                    delay(100)
                    withContext(CoroutineName("withFirstContext")) {
                        try {
                            delay(100)
                            withContext(CoroutineName("withSecondContext")) {
                                try {
                                    printJobsHierarchy(scopeJob)
                                    delay(100)
                                    println("second context completed")
                                } catch (exception: CancellationException) {
                                    println("second context haulted")
                                }
                            }
                            println("first context completed")
                        } catch (exception: CancellationException) {
                            println("first context haulted")
                        }
                    }
                    println("outer context executed")
                } catch (exception: CancellationException) {
                    println("outer context haulted")
                }
            }
            scope.launch {
                delay(250)
                scope.cancel()
            }
            mainJob.join()
            println("test done")
        }
    }

    /*
    Launch new coroutine inside another coroutine, explore the resulting Job's hierarchy, test cancellation
    of the outer scope, explore structured concurrency
     */
    @Test
    fun nestedLaunchBuilders() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + CoroutineName("outer scope") + Dispatchers.IO)
           val job = scope.launch {
                try {
                    delay(100)
                    withContext(CoroutineName("InnerCoroutine") + Dispatchers.IO) {
                        try {
                            delay(100)
                            launch(CoroutineName("inner new coroutine")) {
                                try {
                                    printJobsHierarchy(scopeJob)
                                    delay(100)
//                        scope.cancel()
                                    println("inner new coroutine executed")
                                } catch (ex: CancellationException) {
                                    println("inner new coroutine dropped")
                                }
                            }
                            println("inner coroutine executed")
                        } catch (ex: CancellationException) {
                            println("inner coroutine haulted")
                        }
                    }
                    println("outer coroutine executed")
                } catch (ex: CancellationException) {
                    println("outer coroutine dropped")
                }
            }
            scope.launch {
                delay(250)
                scope.cancel()
            }
            job.join()
            println("test done")
        }
    }

    /*
    Launch new coroutine on "outer scope" inside another coroutine, explore the resulting Job's hierarchy,
    test cancellation of the outer scope, explore structured concurrency
     */
    @Test
    fun nestedCoroutineInOuterScope() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + CoroutineName("outer scope") + Dispatchers.IO)
            val job = scope.launch {
                try {
                    delay(100)
                    withContext(CoroutineName("withContext") + Dispatchers.IO) {
                        try {
                            delay(100)
                            scope.launch(CoroutineName("innerCoroutine")) {
                                try {
                                    printJobsHierarchy(scopeJob)
                                    delay(100)
                                    println("inner coroutine executed")
                                } catch (ex : CancellationException) {
                                    println("inner coroutine haulted")
                                }
                            }
                            println("with context completed")
                        } catch (ex: CancellationException) {
                            println("with context haulted")
                        }
                    }
                    println("outer coroutine executed")
                } catch (ex: CancellationException) {
                    println("outer coroutine haulted")
                }
            }
            scope.launch {
                delay(250)
                scope.cancel()
            }
            job.join()
            println("test done")
        }
    }


}