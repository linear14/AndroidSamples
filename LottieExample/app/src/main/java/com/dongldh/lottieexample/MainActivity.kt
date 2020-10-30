package com.dongldh.lottieexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isChecked = false
    var switchOn = false
    var progress = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lottie_switch.setOnClickListener {
            if(switchOn) {
                progress = lottie_bus.progress
                lottie_bus.cancelAnimation()
                lottie_switch.setMinAndMaxProgress(0.5f, 1.0f)
                lottie_switch.playAnimation()
            } else {
                lottie_bus.playAnimation()
                lottie_bus.progress = progress
                lottie_switch.setMinAndMaxProgress(0.0f, 0.5f)
                lottie_switch.playAnimation()
            }
            switchOn = !switchOn
        }

        lottie_checkbox.setOnClickListener {
            if(isChecked) {
                lottie_checkbox.speed = -1f
                lottie_checkbox.playAnimation()
            } else {
                lottie_checkbox.speed = 1f
                lottie_checkbox.playAnimation()
            }
            isChecked = !isChecked
        }

    }
}