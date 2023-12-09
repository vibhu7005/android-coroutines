package com.jordiee.coroutines

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), com.jordiee.coroutines.common.ToolbarDelegate {

    private lateinit var screensNavigator: com.jordiee.coroutines.common.ScreensNavigator
    private lateinit var btnBack: ImageButton
    private lateinit var txtScreenTitle: TextView

    val compositionRoot by lazy {
        com.jordiee.coroutines.common.dependencyinjection.ActivityCompositionRoot(
            this,
            (application as com.jordiee.coroutines.MyApplication).applicationCompositionRoot
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screensNavigator = compositionRoot.screensNavigator
        screensNavigator.init(savedInstanceState)

        btnBack = findViewById(R.id.btn_back)
        btnBack.setOnClickListener { screensNavigator.navigateUp() }

        txtScreenTitle = findViewById(R.id.txt_screen_title)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        screensNavigator.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (!screensNavigator.navigateBack()) {
            super.onBackPressed()
        }
    }

    override fun setScreenTitle(screenTitle: String) {
        txtScreenTitle.text = screenTitle
    }

    override fun showUpButton() {
        btnBack.visibility = View.VISIBLE
    }

    override fun hideUpButton() {
        btnBack.visibility = View.INVISIBLE
    }
}