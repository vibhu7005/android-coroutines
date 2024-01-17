package com.jordiee.coroutines.demonstrations.cancellationonexception

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.RuntimeException

class CancellationOnExceptionDemoTest {

    @Test
    fun concurrentCoroutines() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + Dispatchers.Default)
            val job1 = scope.launch {
                delay(100)
                println("inside coroutine")
            }
            val job2 = scope.launch {
                delay(50)
            }
            joinAll(job1, job2)
            println("scopeJob: $scopeJob")
            println("job1: $job1")
            println("job2: $job2")
        }
        Thread.sleep(100)
        println("test completed")
    }
    @Test
    fun testCoroutineHierarchy() {
        runBlocking {
            val scope = CoroutineScope(Dispatchers.IO + CoroutineName("Jordiee"))
            println(scope)
            println(scope.coroutineContext)
            println(scope.coroutineContext[Job])
            val job = scope.launch {
                println(this)
                println(this.coroutineContext)
                println(this.coroutineContext[Job])
                withContext(Dispatchers.Default) {
                    println(this)
                    println(this.coroutineContext)
                    println(this.coroutineContext[Job])
                }
            }
            job.join()
        }
    }

    @Test
    fun uncaughtExceptionInConcurrentCoroutines() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + Dispatchers.Default)
            val job1 = scope.launch {
                delay(100)
                println("inside coroutine")
            }
            val job2 = scope.launch {
                delay(50)
                throw RuntimeException()
            }
            joinAll(job1, job2)
            println("scopeJob: $scopeJob")
            println("job1: $job1")
            println("job2: $job2")
        }
        Thread.sleep(100)
        println("test completed")
    }


    @Test
    fun uncaughtExceptionInConcurrentCoroutinesWithExceptionHandler() {
        runBlocking {
            val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
                println("Caught exception: $throwable")
            }

            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + Dispatchers.Default + coroutineExceptionHandler)
            val job1 = scope.launch {
                delay(100)
                println("inside coroutine")
            }
            val job2 = scope.launch {
                delay(50)
                throw RuntimeException()
            }
            joinAll(job1, job2)
            println("scopeJob: $scopeJob")
            println("job1: $job1")
            println("job2: $job2")
        }
        Thread.sleep(100)
        println("test completed")
    }

}