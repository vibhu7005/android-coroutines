package com.jordiee.coroutines.demonstrations.design

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jordiee.coroutines.R
import com.jordiee.coroutines.home.ScreenReachableFromHome
import kotlinx.coroutines.*

class DesignDemoFragment : com.jordiee.coroutines.common.BaseFragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override val screenTitle get() = ScreenReachableFromHome.DESIGN_DEMO.description

    private lateinit var benchmarkUseCase: BenchmarkUseCase

    private lateinit var btnStart: Button
    private lateinit var txtRemainingTime: TextView

    private var hasBenchmarkBeenStartedOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        benchmarkUseCase = compositionRoot.benchmarkUseCase
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_loop_iterations_demo, container, false)

        txtRemainingTime = view.findViewById(R.id.txt_remaining_time)

        btnStart = view.findViewById(R.id.btn_start)
        btnStart.setOnClickListener {
            logThreadInfo("button callback")

            val benchmarkDurationSeconds = 5

            coroutineScope.launch {
                updateRemainingTime(benchmarkDurationSeconds)
            }

            coroutineScope.launch {
                btnStart.isEnabled = false
                val iterationsCount = benchmarkUseCase.executeBenchmark(benchmarkDurationSeconds)
                Toast.makeText(requireContext(), "$iterationsCount", Toast.LENGTH_SHORT).show()
                btnStart.isEnabled = true
            }

            hasBenchmarkBeenStartedOnce = true
        }

        return view
    }

    override fun onStop() {
        logThreadInfo("onStop()")
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        if (hasBenchmarkBeenStartedOnce) {
            btnStart.isEnabled = true
            txtRemainingTime.text = "done!"
        }
    }


    private suspend fun updateRemainingTime(remainingTimeSeconds: Int) {
        for (time in remainingTimeSeconds downTo 0) {
            if (time > 0) {
                logThreadInfo("updateRemainingTime: $time seconds")
                txtRemainingTime.text = "$time seconds remaining"
                delay(1000)
            } else {
                txtRemainingTime.text = "done!"
            }
        }
    }

    private fun logThreadInfo(message: String) {
        com.jordiee.coroutines.common.ThreadInfoLogger.logThreadInfo(message)
    }

    companion object {
        fun newInstance(): Fragment {
            return DesignDemoFragment()
        }
    }
}