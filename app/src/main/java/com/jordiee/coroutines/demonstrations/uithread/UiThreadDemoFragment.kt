package com.jordiee.coroutines.demonstrations.uithread

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jordiee.coroutines.R
import com.jordiee.coroutines.common.ThreadInfoLogger.logThreadInfo
import com.jordiee.coroutines.home.ScreenReachableFromHome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

class UiThreadDemoFragment : com.jordiee.coroutines.common.BaseFragment() {

    override val screenTitle get() = ScreenReachableFromHome.UI_THREAD_DEMO.description

    private lateinit var btnStart: Button
    private lateinit var txtRemainingTime: TextView
    private lateinit var coroutine: CoroutineScope
    private var actionInitiated = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loop_iterations_demo, container, false)
        coroutine = CoroutineScope(Dispatchers.Main.immediate)
        txtRemainingTime = view.findViewById(R.id.txt_remaining_time)

        btnStart = view.findViewById(R.id.btn_start)
        btnStart.setOnClickListener {
            actionInitiated = true
            val benchmarkDurationSeconds = 5
            coroutine.launch {
                updateRemainingTime(benchmarkDurationSeconds)
            }
            coroutine.launch {
                logThreadInfo("button callback")
                btnStart.isEnabled = false
                val iterationsCount = executeBenchmark(benchmarkDurationSeconds)
                Toast.makeText(requireContext(), "$iterationsCount", Toast.LENGTH_SHORT).show()
                btnStart.isEnabled = true
            }
        }
        return view
    }

    private suspend fun executeBenchmark(benchmarkDurationSeconds: Int): Long {
        return withContext(Dispatchers.Default) {
            logThreadInfo("benchmark started")
            val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L
            var iterationsCount: Long = 0
            while (System.nanoTime() < stopTimeNano) {
                iterationsCount++
            }
            logThreadInfo("benchmark completed")
            iterationsCount
        }
    }

    override fun onStop() {
        super.onStop()
        coroutine.coroutineContext.cancelChildren()
        if (actionInitiated) {
            btnStart.isEnabled = true
            txtRemainingTime.text = "done"
        }
    }

    private suspend fun updateRemainingTime(remainingTimeSeconds: Int) {
        var remainingTime = remainingTimeSeconds
        while (remainingTime > 0) {
            logThreadInfo("updateRemainingTime: $remainingTime seconds")
            txtRemainingTime.text = "$remainingTime seconds remaining"
            remainingTime -= 1
            delay(1000)
        }
        txtRemainingTime.text = "done"
    }

    private fun logThreadInfo(message: String) {
        com.jordiee.coroutines.common.ThreadInfoLogger.logThreadInfo(message)
    }

    companion object {
        fun newInstance(): Fragment {
            return UiThreadDemoFragment()
        }
    }
}