package com.dongldh.carrotimagepickerexample.adapter

import android.R
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dongldh.carrotimagepickerexample.util.App

@BindingAdapter("imageFromUri")
fun bindImageFromUri(view: ImageView, imageUri: String?) {
    Glide.with(view.context)
        .load(Uri.parse(imageUri))
        .placeholder(ColorDrawable(ContextCompat.getColor(App.applicationContext(), R.color.white)))
        .transition(DrawableTransitionOptions.withCrossFade())
        .centerCrop()
        .into(view)
}
