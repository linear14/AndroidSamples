package com.dongldh.swipeviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CardAdapter(donateList)
        recycler.adapter = adapter

    }


    companion object {
        val donateList = mutableListOf<Donate>().apply {
            for(i in 0 until 10) {
                add(Donate(i.toLong()))
            }
        }
    }
}