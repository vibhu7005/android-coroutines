package com.jordiee.coroutines.demonstrations.uithread

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import com.jordiee.coroutines.R
import com.jordiee.coroutines.common.ThreadInfoLogger.logThreadInfo
import com.jordiee.coroutines.home.ScreenReachableFromHome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UiThreadDemoFragment : com.jordiee.coroutines.common.BaseFragment() {

    override val screenTitle get() = ScreenReachableFromHome.UI_THREAD_DEMO.description

    private lateinit var btnStart: Button
    private lateinit var txtRemainingTime: TextView
    private lateinit var coroutine: CoroutineScope

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loop_iterations_demo, container, false)
        coroutine = CoroutineScope(Dispatchers.Main.immediate)
        txtRemainingTime = view.findViewById(R.id.txt_remaining_time)

        btnStart = view.findViewById(R.id.btn_start)
        btnStart.setOnClickListener {
            coroutine.launch {
                logThreadInfo("button callback")
                btnStart.isEnabled = false
                updateRemainingTime(5)
                val iterationsCount = 0L
                executeBenchmark(iterationsCount)
                Toast.makeText(requireContext(), "$iterationsCount", Toast.LENGTH_SHORT).show()
                btnStart.isEnabled = true
            }
        }
        return view
    }

    private suspend fun executeBenchmark(iterationsCount : Long) {
        val benchmarkDurationSeconds = 5
        return withContext(Dispatchers.Default) {
            logThreadInfo("benchmark started")
            val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L
            var iterationsCount: Long = 0
            while (System.nanoTime() < stopTimeNano) {
                iterationsCount++
            }
            logThreadInfo("benchmark completed")
            withContext(Dispatchers.Main) {
            }
        }
    }

    private fun updateRemainingTime(remainingTimeSeconds: Int) {
        logThreadInfo("updateRemainingTime: $remainingTimeSeconds seconds")

        if (remainingTimeSeconds > 0) {
            txtRemainingTime.text = "$remainingTimeSeconds seconds remaining"
            Handler(Looper.getMainLooper()).postDelayed({
                updateRemainingTime(remainingTimeSeconds - 1)
            }, 1000)
        } else {
            txtRemainingTime.text = "done!"
        }

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