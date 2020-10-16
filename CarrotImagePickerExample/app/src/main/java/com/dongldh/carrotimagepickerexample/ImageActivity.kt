package com.dongldh.carrotimagepickerexample

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.item_image.image

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val uri = Uri.parse(intent.getStringExtra("uri"))

        Glide.with(this)
            .load(uri)
            .into(image)

        action_back.setOnClickListener { finish() }
    }
}